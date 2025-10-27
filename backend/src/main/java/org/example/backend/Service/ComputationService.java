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
        if (baseStats.getStats() == dinoStats.getStats()) {
            BaseStats.STATS statType = dinoStats.getStats();
            int totalPoints = dinoStats.getValue().getStatPoints() + (dinoStats.getValue().getMutationCount());
            float statTotal = totalPoints * (baseStats.getValue().getIncrementPerPoint());
//          Switch statement to get every single one
            switch (statType) {
                case HEALTH:
                    statTotal *= settings.getHealthScaleFactor();
                    break;
                case WEIGHT:
                    statTotal *= settings.getWeightScaleFactor();
                    break;
                case FOOD:
                    statTotal *= settings.getFoodScaleFactor();
                    break;
                case STAMINA:
                    statTotal *= settings.getStaminaScaleFactor();
                    break;
                case OXYGEN:
                    statTotal *= settings.getOxygenScaleFactor();
                    break;
                case MELEE:
                    statTotal *= settings.getMeleeScaleFactor() * tamingEffectiveness;
                    break;
                default:
                    break;
            }
            stat.setStatType(statType);
            statTotal += baseStats.getValue().getBaseValue();
            stat.setCalcTotal(statTotal);
            stat.setTotalPoints(totalPoints);
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
            settings = new Settings(1L,0f,0f,0f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f,1f, false, false);
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
