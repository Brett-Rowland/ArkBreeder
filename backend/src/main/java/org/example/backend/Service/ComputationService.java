package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLineTransfer;
import org.example.backend.DTOs.DinosaurTransfer;
import org.example.backend.DTOs.StatsTransfer;
import org.example.backend.Domains.*;
import org.example.backend.Repo.BreedingLinesRepo;
import org.example.backend.Repo.DinosaurRepo;
import org.example.backend.Repo.PresetsRepo;
import org.example.backend.Repo.SettingsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ComputationService {

    private final BreedingLinesRepo breedingLinesRepo;
    private final DinosaurRepo dinosaurRepo;
    private final SettingsRepo settingsRepo;
    private final PresetsRepo presetsRepo;

//    public List<StatsTransfer> computeStats (Settings settings, List<BaseStats> baseStats, float tamingEffectivness, List<DinosaurStats> dinosaurStats){
//
//    }






    public BreedingLineTransfer lineComputation(Long lineId){
        BreedingLineTransfer computation = new BreedingLineTransfer();

        return computation;

    }


    public DinosaurTransfer dinoComputation(Long dinoId){
        DinosaurTransfer dinosaurTransfer = new DinosaurTransfer();

        Dinosaur dinosaur = dinosaurRepo.getDinosaurByDinoId(dinoId);

        BreedingLine breedingLine = dinosaur.getBreedingLineId();

        Settings settings = breedingLine.getPresets().getSettings();

        Creature creature = breedingLine.getCreature();




        return dinosaurTransfer;
    }
}
