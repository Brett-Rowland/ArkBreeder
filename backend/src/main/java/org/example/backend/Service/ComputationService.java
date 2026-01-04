package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLineTransfer;
import org.example.backend.DTOs.DinosaurTransfer;
import org.example.backend.DTOs.StatsTransfer;
import org.example.backend.DTOs.ValidationInput;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.BreedingSettings;
import org.example.backend.ValueObjects.Stats;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComputationService {

    private final BreedingLinesRepo breedingLinesRepo;
    private final DinosaurRepo dinosaurRepo;
    private final SettingsRepo settingsRepo;
    private final ServersRepo serversRepo;
    private final CreatureRepo creatureRepo;
    private final BaseStatsRepo baseStatsRepo;
    private final DinosaurStatsRepo dinosaurStatsRepo;


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


    public StatsTransfer calcStats(BreedingSettings settings, BaseStats baseStats, DinosaurStats dinoStats, float tamingEffectiveness){
        StatsTransfer stat = new StatsTransfer();
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


    public BreedingLineTransfer lineComputation(BreedingLine breedingLine){
        BreedingLineTransfer computation = new BreedingLineTransfer();

        return computation;

    }


    public DinosaurTransfer dinoComputation(Long dinoId){
        DinosaurTransfer dinosaurTransfer = new DinosaurTransfer();

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
        List<StatsTransfer> transferComputedStats = new ArrayList<>();
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

    public List<StatsTransfer> validation(ValidationInput validationInput){
//        Need to get my Base Stats first
        List<BaseStats> baseStats = baseStatsRepo.getBaseStatsASCById(validationInput.getCreatureId());
        System.out.println(validationInput.getStats());
        List<StatsTransfer> statsTransfers = validationInput.getStats();
        if (baseStats.size() == statsTransfers.size()) {
            for (int i = 0; i < baseStats.size(); i++) {
                StatsTransfer statsTransfer = statsTransfers.get(i);
                if (statsTransfer.getStatType() == baseStats.get(i).getStats().getStatType()) {
                    statsTransfer.setCalcTotal(calculation(validationInput.getBreedingSettings(), baseStats.get(i), statsTransfer.getTotalPoints(), validationInput.getTamingEffectiveness(), statsTransfer.getStatType()));
                }
            }
        }
        return  statsTransfers;
    }
}
