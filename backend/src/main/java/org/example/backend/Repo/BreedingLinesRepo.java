package org.example.backend.Repo;

import org.example.backend.DTOs.DinosaurColorRegionDTO;
import org.example.backend.DTOs.StatsDTO;
import org.example.backend.Domains.BreedingLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * BreedingLinesRepo.
 *
 * Repository interface for managing {@link BreedingLine} entities.
 *
 * This repository provides data access methods for:
 * - retrieving breeding lines owned by a user
 * - loading breeding line details by ID
 * - calculating aggregated/statistical summaries across dinosaurs in a line
 * - retrieving display-ready color region information for the line
 *
 * Notes:
 * - Several queries return DTO projections (e.g., {@link StatsDTO}, {@link DinosaurColorRegionDTO})
 *   to avoid loading entire entity graphs when only computed/display values are needed.
 * - Pagination for line lists is supported using {@link Pageable}.
 */
@Repository
public interface BreedingLinesRepo extends JpaRepository<BreedingLine, Long> {

    /**
     * Retrieves a breeding line by its ID.
     *
     * @param lineId breeding line identifier
     * @return matching {@link BreedingLine}
     */
    BreedingLine getBreedingLineByBreedingLineId(Long lineId);

    /**
     * Retrieves a paginated list of breeding lines owned by a user (based on token).
     *
     * @param token authentication token identifying the user
     * @param pageable pagination configuration (page number, size, sorting)
     * @return list of {@link BreedingLine} records within the requested page
     */
    @Query("SELECT bl FROM BreedingLine bl WHERE bl.user.token = ?1")
    List<BreedingLine> getBreedingLinesLimit(Long token, Pageable pageable);

    /**
     * Retrieves the maximum point totals per stat type for a breeding line.
     *
     * This query aggregates across all dinosaurs in the breeding line and computes:
     * - max stat points per stat type
     * - including mutation contribution (mutationCount * 2)
     *
     * Returns DTO projection:
     * - {@link StatsDTO} containing:
     *   - statType
     *   - totalPoints (calculated max per stat)
     *
     * @param breedingId breeding line identifier
     * @return ordered list of {@link StatsDTO} containing max points per stat type
     */
    @Query(
            "SELECT new org.example.backend.DTOs.StatsDTO(ds.stats.statType, MAX(ds.stats.statPoints + (ds.stats.mutationCount * 2))) " +
                    "FROM BreedingLine breed " +
                    "JOIN Dinosaur dino ON dino.breedingLineId.breedingLineId = breed.breedingLineId " +
                    "JOIN DinosaurStats ds ON dino.dinoId = ds.dinosaur.dinoId " +
                    "WHERE breed.breedingLineId = ?1 " +
                    "GROUP BY breed.breedingLineId, ds.stats.statType " +
                    "ORDER BY ds.stats.statType"
    )
    List<StatsDTO> getBreedingLineMaxStats(Long breedingId);

    /**
     * Retrieves display-ready color region values for a breeding line.
     *
     * This query returns the color assignments (region -> color name/hex) using a
     * representative dinosaur from the breeding line:
     * - selects the dinosaur with the smallest dinoId in the line
     *
     * Returns DTO projection:
     * - {@link DinosaurColorRegionDTO} containing:
     *   - colorRegion index
     *   - colorName
     *   - hexCode
     *
     * Notes:
     * - This is used for UI display to show what color regions look like for the line
     *   without requiring every dinosaur to be loaded.
     * - The ordering ensures regions are returned in numeric region order.
     *
     * @param breedingId breeding line identifier
     * @return ordered list of {@link DinosaurColorRegionDTO} for display
     */
    @Query(
            "SELECT NEW org.example.backend.DTOs.DinosaurColorRegionDTO(dc.colorRegion, ac.color.colorName, ac.color.colorHex) " +
                    "FROM Dinosaur d " +
                    "JOIN DinoColors dc ON d.dinoId = dc.dinosaur.dinoId " +
                    "JOIN ArkColors ac ON dc.arkColor.colorId = ac.colorId " +
                    "JOIN BreedingLine bl ON bl.breedingLineId = d.breedingLineId.breedingLineId " +
                    "WHERE bl.breedingLineId = :breedingId " +
                    "AND d.dinoId = (" +
                    "  SELECT MIN(d2.dinoId) " +
                    "  FROM Dinosaur d2 " +
                    "  WHERE d2.breedingLineId.breedingLineId = :breedingId" +
                    ") " +
                    "ORDER BY dc.colorRegion"
    )
    List<DinosaurColorRegionDTO> getDisplayColorRegions(Long breedingId);
}
