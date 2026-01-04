package org.example.backend.Repo;

import org.example.backend.DTOs.ColorRegionTransfer;
import org.example.backend.DTOs.StatsTransfer;
import org.example.backend.Domains.BreedingLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.util.List;

@Repository
public interface BreedingLinesRepo extends JpaRepository<BreedingLine, Long> {
    BreedingLine getBreedingLineByBreedingLineId(Long lineId);

    @Query("Select bl from BreedingLine bl where bl.user.userId = ?1")
    List<BreedingLine> getBreedingLinesByUsersId(Long userId);


    @Query("SELECT bl from BreedingLine bl where bl.user.token = ?1")
    List<BreedingLine> getBreedingLinesLimit(Long token, Pageable pageable);


    @Query("SELECT new org.example.backend.DTOs.StatsTransfer(ds.stats.statType, MAX(ds.stats.statPoints + (ds.stats.mutationCount * 2))) from BreedingLine breed " +
            "JOIN Dinosaur dino on dino.breedingLineId.breedingLineId = breed.breedingLineId " +
            "JOIN DinosaurStats ds on dino.dinoId = ds.dinosaur.dinoId " +
            "WHERE breed.breedingLineId = ?1 " +
            "GROUP BY breed.breedingLineId, ds.stats.statType " +
            "ORDER BY ds.stats.statType")
    List<StatsTransfer> getBreedingLineMaxStats(Long breedingId);


    @Query("SELECT NEW org.example.backend.DTOs.ColorRegionTransfer(dc.colorRegion, ac.color.colorName, ac.color.colorHex) FROM Dinosaur d " +
            "JOIN DinoColors dc on d.dinoId = dc.dinosaur.dinoId " +
            "JOIN ArkColors ac on dc.arkColor.colorId = ac.colorId " +
            "JOIN BreedingLine bl on bl.breedingLineId  = d.breedingLineId.breedingLineId " +
            "WHERE bl.breedingLineId = :breedingId " +
            "AND d.dinoId = (" +
            "SELECT MIN(d2.dinoId) " +
            "FROM Dinosaur d2 " +
            "WHERE d2.breedingLineId.breedingLineId = :breedingId ) " +
            "ORDER BY dc.colorRegion" )
    List<ColorRegionTransfer> getDisplayColorRegions(Long breedingId);

}
