package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLineTransfer;
import org.example.backend.DTOs.DinosaurTransfer;
import org.example.backend.DTOs.StatsTransfer;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComputationService {

    private final BreedingLinesRepo breedingLinesRepo;
    private final DinosaurRepo dinosaurRepo;
    private final SettingsRepo settingsRepo;
    private final PresetsRepo presetsRepo;
    private final CreatureRepo creatureRepo;
    private final BaseStatsRepo baseStatsRepo;
    private final DinosaurStatsRepo dinosaurStatsRepo;

    public StatsTransfer calcStats(Settings settings, BaseStats baseStats, DinosaurStats dinoStats, float tamingEffectiveness){
        StatsTransfer stat = new StatsTransfer();
        if (baseStats.getStatType() == dinoStats.getStatType()) {
            BaseStats.STATS statType = dinoStats.getStatType();
            float finalStat = 0.0f;
            float baseValue = baseStats.getStats().getBaseValue();
            float incrementValue = baseStats.getStats().getIncrementPerPoint();
            float wildPoints = dinoStats.getStats().getStatPoints();
            float mutationPoints = dinoStats.getStats().getMutationCount();
            float totalPoints = wildPoints + mutationPoints * 2;
//          Switch statement to get every single one
            switch (statType) {
                case HEALTH:
                    finalStat = ((baseValue * (1 + totalPoints * incrementValue * settings.getHealthScaleFactor())) * (1 + baseStats.getStats().getStatMultiplicand())) + (baseStats.getStats().getStatAdditive() * settings.getHealthAdditive());
                    break;
                case STAMINA:
                    finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getStaminaScaleFactor());
                    break;
                case FOOD:
                    finalStat = (baseValue * (1 + totalPoints * incrementValue * settings.getFoodScaleFactor())) * (1 + tamingEffectiveness * baseStats.getStats().getStatMultiplicand() * settings.getFoodAffinity());
                    break;
                case WEIGHT:
                    finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getWeightScaleFactor());
                    break;
                case OXYGEN:
                    finalStat = baseValue * (1 + totalPoints * incrementValue * settings.getOxygenScaleFactor());
                    break;
                case MELEE:
                    finalStat = (baseValue * (1 + (totalPoints * incrementValue * settings.getMeleeScaleFactor())) + baseStats.getStats().getStatAdditive() * settings.getMeleeAdditive()) * (1 + (tamingEffectiveness * baseStats.getStats().getStatMultiplicand() * settings.getMeleeAffinity()));
                    finalStat = Math.round(finalStat*1000.0)/10.0f;
                    break;
                default:
//                  Crafting
                    finalStat = baseValue * (1 + totalPoints * incrementValue);
                    break;
            }
            stat.setStatType(statType);
            stat.setTotalPoints((int) totalPoints);
            stat.setCalcTotal(finalStat);
        }
        return stat;
    }


    public BreedingLineTransfer lineComputation(Long lineId){
        BreedingLineTransfer computation = new BreedingLineTransfer();

        return computation;

    }


    public DinosaurTransfer dinoComputation(Long dinoId){
        DinosaurTransfer dinosaurTransfer = new DinosaurTransfer();

        Dinosaur dinosaur = dinosaurRepo.getDinosaurByDinoId(dinoId);

        BreedingLine breedingLine = dinosaur.getBreedingLineId();

        Presets preset = breedingLine.getPresets();

        Settings settings;
        if (preset == null){
            settings = new Settings(1L,0f,0f,0f,1f,1f,1f,1f,1f,1f,.14f,.4f,.44f,.14f,.44f, false, false);
        }
        else{
            settings = preset.getSettings();
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
}
