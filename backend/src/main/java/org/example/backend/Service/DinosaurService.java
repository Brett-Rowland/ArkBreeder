package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.DinosaurInput;
import org.example.backend.DTOs.DinosaurPageDTO;
import org.example.backend.Domains.*;
import org.example.backend.Repo.*;
import org.example.backend.ValueObjects.StatPoints;
import org.example.backend.ValueObjects.Stats;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DinosaurService.
 *
 * Service layer responsible for managing individual dinosaur instances
 * within a breeding line.
 *
 * Responsibilities:
 * - create dinosaurs within a breeding line (including stat points and color assignments)
 * - update dinosaur data (nickname, stats, colors)
 * - delete dinosaurs
 * - rename dinosaurs
 * - retrieve a dinosaur by ID
 *
 * Notes:
 * - A Dinosaur is an instance-level record tied to a {@link BreedingLine},
 *   not a species definition.
 * - Dinosaur creation persists multiple related tables:
 *   - {@link Dinosaur}
 *   - {@link DinosaurStats}
 *   - {@link DinoColors}
 * - Color validation ensures the submitted regions match the creature’s defined regions.
 */
@Service
@AllArgsConstructor
public class DinosaurService {

    /** Repository for breeding line lookup and ownership context. */
    BreedingLinesRepo breedingLinesRepo;

    /** Repository for dinosaur persistence and retrieval. */
    DinosaurRepo dinosaurRepo;

    /** Repository for dinosaur-to-color join entities. */
    DinoColorsRepo dinoColorsRepo;

    /** Repository for per-dinosaur stat point allocations. */
    DinosaurStatsRepo dinosaurStatsRepo;

    /** Repository used to retrieve valid creature color region indices. */
    ColorRegionRepo colorRegionsRepo;

    /** Repository used to resolve Ark color IDs into {@link ArkColors} reference data. */
    ArkColorsRepo arkColorsRepo;

    private static final PageRequest pageDefault = PageRequest.of(0, 50);

    /**
     * Creates a new dinosaur in a breeding line.
     *
     * Workflow:
     * - load breeding line by ID
     * - validate incoming color region count against the creature's defined regions
     * - resolve all Ark color IDs in bulk and map them by colorId for fast lookup
     * - build {@link DinoColors} list (one per region) tied to the new dinosaur
     * - build {@link DinosaurStats} list tied to the new dinosaur
     * - persist the dinosaur, then persist dependent lists
     *
     * Validation rules:
     * - submitted region count must match the creature region count
     * - submitted region indices must match expected creature region indices in order
     * - submitted Ark color IDs must exist in reference table ({@link ArkColors})
     *
     * Persisted effects:
     * - writes to dinosaur table and dependent join tables for stats and colors
     *
     * @param dinosaur incoming dinosaur creation payload
     * @param lineId breeding line identifier the dinosaur belongs to
     * @throws Exception if input validation fails or color resolution fails
     */
    public void createDinosaur(DinosaurInput dinosaur, Long lineId) throws Exception {

        Dinosaur newDinosaur = new Dinosaur();
        newDinosaur.setDinosaurNickname(dinosaur.getNickname());

        // Resolve breeding line and expected creature color regions
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);
        newDinosaur.setBreedingLineId(breedingLine);

        int[] colorRegions = colorRegionsRepo.getColorRegionsByCreatureSort(breedingLine.getCreature());
        int[][] dinosaurColors = dinosaur.getColors();

        // Validate region count matches the creature definition
        if (colorRegions.length != dinosaurColors.length) {
            throw new Exception("Incorrect Color Region Amount");
        }

        // Bulk resolve ArkColor IDs used in the payload
        Set<Long> arkColor = Arrays.stream(dinosaurColors)
                .map(row -> (long) row[1])
                .collect(Collectors.toSet());

        List<ArkColors> arkColors = arkColorsRepo.getArkColorsByColorIds(arkColor);

        // Map colors by colorId for fast lookup during join table creation
        Map<Long, ArkColors> arkColorsMap = arkColors.stream()
                .collect(Collectors.toMap(ArkColors::getColorId, color -> color));

        List<DinoColors> dinoColorsList = new ArrayList<>();
        List<DinosaurStats> dinosaurStatsList = new ArrayList<>();

        // Build dinosaur color assignments (join table records)
        for (int i = 0; i < dinosaurColors.length; i++) {
            DinoColors dinoColors = new DinoColors();
            dinoColors.setDinosaur(newDinosaur);

            // Validate expected region ordering
            if (colorRegions[i] != dinosaurColors[i][0]) {
                throw new Exception("Incorrect Color Region");
            }

            dinoColors.setColorRegion(dinosaurColors[i][0]);
            dinoColors.setArkColor(arkColorsMap.get((long) dinosaurColors[i][1]));

            if (dinoColors.getArkColor() == null) {
                throw new Exception("No Color Matching");
            }

            dinoColorsList.add(dinoColors);
        }

        // Build dinosaur stat point allocations
        for (int[] ds : dinosaur.getStats()) {
            DinosaurStats dinosaurStats = new DinosaurStats();
            dinosaurStats.setDinosaur(newDinosaur);

            // ds[0] = stat type ordinal, ds[1] = wild points, ds[2] = mutation count
            StatPoints stats = new StatPoints(ds[1], ds[2]);
            stats.setStatType(Stats.STATS.values()[ds[0]]);
            dinosaurStats.setStats(stats);

            dinosaurStatsList.add(dinosaurStats);
        }

        // Persist parent first, then dependent rows
        dinosaurRepo.save(newDinosaur);
        dinosaurStatsRepo.saveAll(dinosaurStatsList);
        dinoColorsRepo.saveAll(dinoColorsList);
    }

    /**
     * Deletes a dinosaur by ID (hard delete).
     *
     * Persisted effects:
     * - removes the dinosaur record.
     *
     * Notes:
     * - If you later switch to soft delete, this method should be adjusted
     *   to set a deletion flag rather than deleting the row.
     *
     * @param dinoId dinosaur identifier
     */
    public void deleteDinosaur(Long dinoId) {
        dinosaurRepo.deleteById(dinoId);
    }

    /**
     * Renames a dinosaur.
     *
     * Persisted effects:
     * - updates {@link Dinosaur#dinosaurNickname}.
     *
     * @param dinoId dinosaur identifier
     * @param newName new nickname to apply
     */
    public void renameDinosaur(Long dinoId, String newName) {
        Dinosaur dino = dinosaurRepo.getDinosaurByDinoId(dinoId);
        dino.setDinosaurNickname(newName);
        dinosaurRepo.save(dino);
    }

    /**
     * Retrieves a dinosaur by ID.
     *
     * @param dinoId dinosaur identifier
     * @return matching {@link Dinosaur} entity
     */
    public Dinosaur grabDino(Long dinoId) {
        return dinosaurRepo.getDinosaurByDinoId(dinoId);
    }



    public DinosaurPageDTO buildDinosaurPage(Long lineId){
        DinosaurPageDTO dinosaurPageDTO = new DinosaurPageDTO();

//        First need to grab the Breeding Line Details
        BreedingLine breedingLine = breedingLinesRepo.getBreedingLineByBreedingLineId(lineId);

//        Set the settings and Creature up
        dinosaurPageDTO.setSettingsDTO(breedingLine.getServer().convertToSettingsDTO());

//        Grab the creatures details
//
        dinosaurPageDTO.setCreatureDTO(breedingLine.getCreature().toDTO());


//        Grabbing all dinosaurs now for move over
        List<Dinosaur> dinosaurs = dinosaurRepo.getDinosaurByBreedingLineId(breedingLine, pageDefault);

        for (Dinosaur dinosaur : dinosaurs) {
            System.out.println(dinosaur.getDinosaurNickname());
        }

        return dinosaurPageDTO;
    }
}
