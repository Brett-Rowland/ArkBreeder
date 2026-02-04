package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.DinosaurColorRegionDTO;
import org.example.backend.DTOs.DinosaurDTO;
import org.example.backend.DTOs.StatsDTO;
import org.example.backend.DTOs.ValidationInput;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.Stats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO: dinoComputation update Color Regions to bring back actual colors assigned to each region
 *
 * One sample
 *
 *  "dinoColorID": 5,
 *  "colorRegion": 4
 *
 * Should be
 *  "dinoColorID": 5,
 *  "colorRegion": 4
 *
 *
 * */



/**
 * ComputationService.
 *
 * Central service responsible for all stat computation and validation math.
 *
 * Responsibilities:
 * - compute final stat values using:
 *   - creature base stats ({@link BaseStats})
 *   - breeding/server settings ({@link BreedingSettings})
 *   - point allocations (wild points + mutation points)
 *   - taming effectiveness where applicable
 * - build computed dinosaur payloads for display ({@link DinosaurDTO})
 * - compute validation values for creature stat validation workflows
 *
 * Notes / assumptions:
 * - This service does not persist computed values; results are generated dynamically.
 * - Several methods assume base stats and per-dinosaur stats are returned in the same
 *   stat type ordering (ASC). Repositories enforce ordering via queries.
 * - Some stat types share formulas (e.g., stamina/charge capacity).
 */

@Service
@AllArgsConstructor
public class ComputationService {

    private final DinosaurRepo dinosaurRepo;
    private final BaseStatsRepo baseStatsRepo;
    private final DinosaurStatsRepo dinosaurStatsRepo;

    /**
     * Core stat calculation function.
     *
     * Computes the final stat value for a given stat type using:
     * - base stat values (base value, increment per point, additive/multiplicand)
     * - total point allocation (wild points + mutation points * 2)
     * - breeding/server settings multipliers
     * - taming effectiveness where applicable (e.g., food, melee)
     *
     * Rounding rules:
     * - MELEE uses a distinct rounding rule (rounded differently than other stats).
     * - Most non-melee stats are rounded to 1 decimal place.
     *
     * @param settings breeding/server configuration values
     * @param baseStats base stat definition for a specific stat type
     * @param totalPoints total points applied to the stat (wild + mutation*2)
     * @param tamingEffectiveness taming effectiveness used in select stat formulas
     * @param statType stat type being calculated
     * @return final computed stat total
     */

    public float calculation(BreedingSettings settings, BaseStats baseStats, float totalPoints, float tamingEffectiveness, Stats.STATS statType){
        float baseValue = baseStats.getStats().getBaseValue();
        float incrementValue = baseStats.getStats().getIncrementPerPoint();
        float effectiveTE = tamingEffectiveness;
        float effectiveSettingsMult = settings.getStatAffinity();

        if (statType == Stats.STATS.HEALTH && baseStats.getStats().getStatMultiplicand() != 0){
            effectiveTE = 1.0f;
            effectiveSettingsMult = 1;
        }

        double finalStat = ((baseValue * (1 + totalPoints * incrementValue * settings.getWildScale()) + baseStats.getStats().getStatAdditive() * settings.getStatAdditive()) * (1 + effectiveTE * baseStats.getStats().getStatMultiplicand() * effectiveSettingsMult));
//        switch (statType) {
//            case HEALTH:
//                finalStat = ((baseValue * (1 + totalPoints * incrementValue * settings.getHealthScaleFactor())) * (1 + baseStats.getStats().getStatMultiplicand())) + (baseStats.getStats().getStatAdditive() * settings.getHealthAdditive());
//                break;
//            case STAMINA:
//            case CHARGE_CAPACITY:
//                finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getStaminaScaleFactor());
//                break;
//            case OXYGEN:
//            case CHARGE_REGENERATION:
//                finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getOxygenScaleFactor());
//                break;
//            case FOOD:
//                finalStat = (baseValue * (1 + totalPoints * incrementValue * settings.getFoodScaleFactor())) * (1 + tamingEffectiveness * baseStats.getStats().getStatMultiplicand() * settings.getFoodAffinity());
//                break;
//            case WEIGHT:
//                finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getWeightScaleFactor());
//                break;
//            case MELEE:
//            case CHARGE_EMISSION_RANGE:
//                finalStat = (baseValue * (1 + (totalPoints * incrementValue * settings.getMeleeScaleFactor())) + baseStats.getStats().getStatAdditive() * settings.getMeleeAdditive()) * (1 + (tamingEffectiveness * baseStats.getStats().getStatMultiplicand() * settings.getMeleeAffinity()));
//                break;
//            default:
//                  Crafting
//                     finalStat = baseValue * (1 + totalPoints * incrementValue);
//
//                break;
//        }
        if (statType != Stats.STATS.MELEE){
            finalStat = Math.round(finalStat*10.0)/10.0f;
        }
        else{
            finalStat = Math.round(finalStat*1000.0)/10.0f;
        }

        return (float) finalStat;
    }

    public float calculateCrafting(BaseStats bs, float totalPoints){
        return bs.getStats().getBaseValue() * (1 + totalPoints * bs.getStats().getIncrementPerPoint());
    }

    public Boolean isLightPet(Stats.STATS statType){
        return statType == Stats.STATS.CHARGE_CAPACITY || statType == Stats.STATS.CHARGE_REGENERATION || statType == Stats.STATS.CHARGE_EMISSION_RANGE;
    }

    public Stats.STATS getLightPetStats(Stats.STATS statType){
        return switch (statType) {
            case CHARGE_CAPACITY -> Stats.STATS.STAMINA;
            case CHARGE_REGENERATION -> Stats.STATS.OXYGEN;
            case CHARGE_EMISSION_RANGE -> Stats.STATS.MELEE;
            default -> statType;
        };
    }

    /**
     * Computes and returns a fully computed dinosaur view ({@link DinosaurDTO}).
     *
     * Workflow:
     * - load dinosaur by ID
     * - resolve breeding line -> creature -> server/settings context
     * - load creature base stats (ordered)
     * - load dinosaur stat points (ordered)
     * - compute derived stat totals per stat type
     * - attach colors and basic dinosaur metadata
     *
     * Default settings:
     * - if the breeding line has no server assigned, fallback {@link BreedingSettings}
     *   defaults are used.
     *
     * Assumptions:
     * - baseStats and dinosaurStats are returned in the same stat type order
     *   and are the same size.
     *
     * @param dinoId dinosaur identifier
     * @return computed {@link DinosaurDTO} for display/usage
     */
    public DinosaurDTO dinoComputation(Long dinoId){
        DinosaurDTO dinosaurTransfer = new DinosaurDTO();
        Dinosaur dinosaur = dinosaurRepo.getDinosaurByDinoId(dinoId);
        BreedingLine breedingLine = dinosaur.getBreedingLineId();
        Servers server = breedingLine.getServer();
        Creature creature = breedingLine.getCreature();

//        Grab Defaults and Base Stats for everything
        List<BreedingSettings> settings  = server.getBreedingSettings();
        List<BaseStats> baseStats = creature.getBaseStats();
        List<DinosaurStats> dinosaurStats = dinosaur.getDinosaurStats();
        List<StatsDTO> transferComputedStats = new ArrayList<>();



//        Get Hashmaps for both the baseStats and Breeding Settings

        Map<Stats.STATS, BaseStats> baseStatsMap = baseStats.stream().collect(Collectors.toMap(
                bs -> bs.getStats().getStatType(),
                bs -> bs,
                (a,b) -> a
        ));

        Map<Stats.STATS, BreedingSettings> breedingSettingsMap = settings.stream().collect(Collectors.toMap(
                BreedingSettings::getStats,
                bs -> bs,
                (a,b) -> a
        ));

        for (DinosaurStats dinosaurStat : dinosaurStats) {
            Stats.STATS statType = dinosaurStat.getStats().getStatType();
            StatsDTO statsDTO = new StatsDTO();
            statsDTO = statsDTO.setUpStatsDTO(dinosaurStat);

//            Grab the base stat matching
            BaseStats baseStat = baseStatsMap.get(statType);
            if (baseStat == null){
                continue;
            }

            if (Stats.STATS.CRAFTING == statType) {
                statsDTO.setCalcTotal(calculateCrafting(baseStat, statsDTO.getTotalPoints()));
                continue;
            }


//            Grab the breeding Stats
            BreedingSettings breedingSettings = breedingSettingsMap.get(statType);
            if (isLightPet(statType)) {
                breedingSettings = breedingSettingsMap.get(getLightPetStats(statType));
            }

            if (breedingSettings == null) {
                continue;
            }

            statsDTO.setCalcTotal(calculation(breedingSettings, baseStat, statsDTO.getTotalPoints(), dinosaur.getTamingEffectiveness(), statType));

            transferComputedStats.add(statsDTO);

        }

        dinosaurTransfer.setStats(transferComputedStats);

        List<DinosaurColorRegionDTO> dinosaurColorRegionDTOS = new ArrayList<>();
        for (DinoColors dinoColors : dinosaur.getDinoColors()) {
            dinosaurColorRegionDTOS.add(dinoColors.toDTO());
        }
        dinosaurTransfer.setColorRegions(dinosaurColorRegionDTOS);
        dinosaurTransfer.setDinoId(dinosaur.getDinoId());
        dinosaurTransfer.setDinosaurNickname(dinosaur.getDinosaurNickname());
        return dinosaurTransfer;
    }

    /**
     * Performs validation calculations for creature stat validation workflows.
     *
     * Workflow:
     * - load base stats for the creature (ordered)
     * - iterate through provided {@link StatsDTO} list (expected to match stat ordering)
     * - compute calcTotal for each stat entry using {@link #calculation}
     *
     * Assumptions:
     * - base stat list size equals incoming stats list size
     * - stat types align by index (same stat order)
     *
     * @param validationInput input payload containing settings, stats, creatureId, and TE
     * @return updated list of {@link StatsDTO} with calcTotal populated
     */
    public List<StatsDTO> validation(ValidationInput validationInput){
//        Need to get my Base Stats first
        List<BaseStats> baseStats = baseStatsRepo.getBaseStatsASCById(validationInput.getCreatureId());
        List<StatsDTO> statsTransfers = validationInput.getStats();
        List<BreedingSettings> breedingSettings = validationInput.getBreedingSettings();

//        Build up hashmaps for both the breeding Settings and baseStats. Cycle through the entire statsTransfers grabbing the responsible I

        Map<Stats.STATS, BaseStats> baseByType = baseStats.stream().collect(Collectors.toMap(
                bs -> bs.getStats().getStatType(),
                bs-> bs,
                (a,b) -> a
        ));


        Map<Stats.STATS, BreedingSettings> breedingSettingsMap = breedingSettings.stream().collect(Collectors.toMap(
                BreedingSettings::getStats,
                bs -> bs,
                (a,b) -> a
        ));


        for (StatsDTO statsDTO : statsTransfers) {
            Stats.STATS statType =  statsDTO.getStatType();

//            Grab the Base Stats
            BaseStats bs =  baseByType.get(statType);
            if (bs == null) {
                continue; // log if you want
            }

//            Check to see if it is Crafting if so calculate it
            if (statType == Stats.STATS.CRAFTING){
                statsDTO.setCalcTotal(calculateCrafting(bs, statsDTO.getTotalPoints()));
                continue;
            }

            BreedingSettings breedingSetting;

//            Check to see if it is lightPet
            if (isLightPet(statType)) {
                breedingSetting = breedingSettingsMap.get(getLightPetStats(statType));
            }
            else{
                breedingSetting = breedingSettingsMap.get(statType);
            }
            if (breedingSetting == null) {
                continue; // log if you want
            }

            statsDTO.setCalcTotal(calculation(breedingSetting, bs, statsDTO.getTotalPoints(), validationInput.getTamingEffectiveness(), statType));
        }
        return  statsTransfers;
    }
}
