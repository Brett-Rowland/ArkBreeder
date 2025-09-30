package org.example.backend.Service;


import lombok.AllArgsConstructor;
import org.example.backend.DTOs.DinosaurTransfer;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.Stats;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DinosaurService {

    BreedingLinesRepo breedingLinesRepo;
    DinosaurRepo dinosaurRepo;
    DinoColorsRepo dinoColorsRepo;
    DinosaurStatsRepo dinosaurStatsRepo;
    ColorRegionRepo colorRegionsRepo;
    ArkColorsRepo arkColorsRepo;


    public void createDinosaur(DinosaurTransfer dinosaur, Long lineId) throws Exception {
        Dinosaur newDinosaur = new Dinosaur();
        newDinosaur.setDinosaurNickname(dinosaur.getNickname());
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        newDinosaur.setBreedingLineId(breedingLine);
        int[] colorRegions = colorRegionsRepo.getColorRegionsByCreatureSort(breedingLine.getCreature());
        int [][] dinosaurColors = dinosaur.getColors();

        if (colorRegions.length != dinosaurColors.length){
            throw new Exception("Incorrect Color Region Amount");
        }

        Set<Long> arkColor = Arrays.stream(dinosaurColors).map(row -> (long) row[1]).collect(Collectors.toSet());

        List<ArkColors> arkColors = arkColorsRepo.getArkColorsByColorIds(arkColor);

        Map<Long, ArkColors> arkColorsMap = arkColors.stream().collect(Collectors.toMap(ArkColors::getColorId, color -> color));

        List<DinoColors> dinoColorsList = new  ArrayList<>();
        List<DinosaurStats> dinosaurStatsList = new  ArrayList<>();

        for (int i = 0; i < dinosaurColors.length; i++){
            DinoColors dinoColors = new DinoColors();
            dinoColors.setDinosaur(newDinosaur);
            if (colorRegions[i] != dinosaurColors[i][0]){
                throw new Exception("Incorrect Color Region");
            }
            dinoColors.setColorRegion(dinosaurColors[i][0]);
            dinoColors.setArkColor(arkColorsMap.get((long) dinosaurColors[i][1]));

            if (dinoColors.getArkColor() == null)
                throw new Exception("No Color Matching");
            dinoColorsList.add(dinoColors);
        }


        for (int[] ds: dinosaur.getStats()) {
            DinosaurStats dinosaurStats = new DinosaurStats();
            dinosaurStats.setDinosaur(newDinosaur);
            dinosaurStats.setStats(BaseStats.STATS.values()[ds[0]]);
            Stats stats = new Stats(ds[1], ds[2]);
            dinosaurStats.setValue(stats);
            dinosaurStatsList.add(dinosaurStats);

        }
        dinosaurRepo.save(newDinosaur);
        dinosaurStatsRepo.saveAll(dinosaurStatsList);
        dinoColorsRepo.saveAll(dinoColorsList);
    }

    public void updateDinosaur(Dinosaur dinosaur, Long dinoId){
        Dinosaur dino = dinosaurRepo.getDinosaurByDinoId(dinoId);
        dino.setDinosaurNickname(dinosaur.getDinosaurNickname());
        dino.setDinoColors(dinosaur.getDinoColors());
        dinoColorsRepo.deleteAll(dinosaur.getDinoColors());
        dino.setDinosaurStats(dinosaur.getDinosaurStats());
        dinosaurStatsRepo.deleteAll(dinosaur.getDinosaurStats());
        dinosaurRepo.save(dinosaur);

    }

    public void deleteDinosaur(Long dinoId){
        dinosaurRepo.deleteById(dinoId);
    }

    public void renameDinosaur(Long dinoId, String newName) {
        Dinosaur dino = dinosaurRepo.getDinosaurByDinoId(dinoId);
        dino.setDinosaurNickname(newName);
        dinosaurRepo.save(dino);
    }

    public Dinosaur grabDino(Long dinoId) {
        return dinosaurRepo.getDinosaurByDinoId(dinoId);
    }
}
