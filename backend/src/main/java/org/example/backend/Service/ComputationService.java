package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.DinosaurDTO;
import org.example.backend.DTOs.StatsDTO;
import org.example.backend.DTOs.ValidationInput;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.BreedingSettings;
import org.example.backend.ValueObjects.Stats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        float finalStat = 0.0f;
        float baseValue = baseStats.getStats().getBaseValue();
        float incrementValue = baseStats.getStats().getIncrementPerPoint();
        switch (statType) {
            case HEALTH:
                finalStat = ((baseValue * (1 + totalPoints * incrementValue * settings.getHealthScaleFactor())) * (1 + baseStats.getStats().getStatMultiplicand())) + (baseStats.getStats().getStatAdditive() * settings.getHealthAdditive());
                break;
            case STAMINA:
            case CHARGE_CAPACITY:
                finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getStaminaScaleFactor());
                break;
            case OXYGEN:
            case CHARGE_REGENERATION:
                finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getOxygenScaleFactor());
                break;
            case FOOD:
                finalStat = (baseValue * (1 + totalPoints * incrementValue * settings.getFoodScaleFactor())) * (1 + tamingEffectiveness * baseStats.getStats().getStatMultiplicand() * settings.getFoodAffinity());
                break;
            case WEIGHT:
                finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getWeightScaleFactor());
                break;
            case MELEE:
            case CHARGE_EMISSION_RANGE:
                finalStat = (baseValue * (1 + (totalPoints * incrementValue * settings.getMeleeScaleFactor())) + baseStats.getStats().getStatAdditive() * settings.getMeleeAdditive()) * (1 + (tamingEffectiveness * baseStats.getStats().getStatMultiplicand() * settings.getMeleeAffinity()));
                finalStat = Math.round(finalStat*1000.0)/10.0f;
                break;
            default:
//                  Crafting
                finalStat = baseValue * (1 + totalPoints * incrementValue);

                break;

        }
        if (statType != Stats.STATS.MELEE){
            finalStat = Math.round(finalStat*10.0)/10.0f;
        }

        return finalStat;
    }

    /**
     * Computes a single {@link StatsDTO} entry for a dinosaur stat record.
     *
     * This method:
     * - confirms the stat type matches the base stat definition
     * - computes total points = wildPoints + (mutationCount * 2)
     * - calls {@link #calculation} to compute the final derived stat value
     *
     * @param settings breeding/server configuration values
     * @param baseStats base stat definition for a stat type
     * @param dinoStats dinosaur stat point allocation for the same stat type
     * @param tamingEffectiveness taming effectiveness used for select formulas
     * @return computed {@link StatsDTO} containing stat type, points, and computed total
     */
    public StatsDTO calcStats(BreedingSettings settings, BaseStats baseStats, DinosaurStats dinoStats, float tamingEffectiveness){
        StatsDTO stat = new StatsDTO();
        if (baseStats.getStats().getStatType() == dinoStats.getStats().getStatType()) {
            Stats.STATS statType = dinoStats.getStats().getStatType();
            float wildPoints = dinoStats.getStats().getStatPoints();
            float mutationPoints = dinoStats.getStats().getMutationCount();
            float totalPoints = wildPoints + (mutationPoints * 2);
            float finalStat = calculation(settings, baseStats, totalPoints, tamingEffectiveness, statType);
            stat.setStatType(statType);
            stat.setTotalPoints((int) totalPoints);
            stat.setCalcTotal(finalStat);
        }
        return stat;
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

        BreedingSettings settings;
        if (server == null){
            settings = new BreedingSettings(1f,1f,1f,1f,1f,1f,.14f,.4f,1f,.14f,.44f);
        }
        else{
            settings = server.getSettings().getBreedingSettings();
        }
        Creature creature = breedingLine.getCreature();

//        Grab Defaults and Base Stats for everything

        List<BaseStats> baseStats = baseStatsRepo.getBaseStatsASC(creature);
        List<DinosaurStats> dinosaurStats = dinosaurStatsRepo.getDinosaurStatsASC(dinosaur);
        List<StatsDTO> transferComputedStats = new ArrayList<>();
        if (baseStats.size() == dinosaurStats.size()) {
            for (int i = 0; i < baseStats.size(); i++) {
                transferComputedStats.add(calcStats(settings, baseStats.get(i), dinosaurStats.get(i), dinosaur.getTamingEffectiveness()));
            }
            dinosaurTransfer.setStats(transferComputedStats);
        }

        dinosaurTransfer.setColorRegions(dinosaur.getDinoColors());
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
        if (baseStats.size() == statsTransfers.size()) {
            for (int i = 0; i < baseStats.size(); i++) {
                StatsDTO statsTransfer = statsTransfers.get(i);
                if (statsTransfer.getStatType() == baseStats.get(i).getStats().getStatType()) {
                    statsTransfer.setCalcTotal(calculation(validationInput.getBreedingSettings(), baseStats.get(i), statsTransfer.getTotalPoints(), validationInput.getTamingEffectiveness(), statsTransfer.getStatType()));
                }
            }
        }
        return  statsTransfers;
    }
}
