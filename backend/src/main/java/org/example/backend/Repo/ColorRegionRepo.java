package org.example.backend.Repo;

import org.example.backend.Domains.ColorRegions;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ColorRegionRepo.
 *
 * Repository interface for managing {@link ColorRegions} entities.
 *
 * This repository provides read access to color region definitions
 * associated with creature species. Color regions define which
 * color slots exist on a creature and how they are indexed.
 *
 * Queries in this repository are primarily used during validation
 * and UI initialization workflows.
 *
 * Notes:
 * - Color regions are defined at the creature (species) level.
 * - Returned values are used to determine valid region indices.
 */
@Repository
public interface ColorRegionRepo extends JpaRepository<ColorRegions, Integer> {

    /**
     * Retrieves all color region indices for a given creature species,
     * ordered by region index.
     *
     * This query returns only the numeric region values rather than
     * full {@link ColorRegions} entities, making it lightweight and
     * suitable for validation and setup logic.
     *
     * @param creature creature species entity
     * @return array of color region indices sorted in ascending order
     */
    @Query("SELECT dcr.colorRegion FROM ColorRegions dcr WHERE dcr.creature = ?1 ORDER BY dcr.colorRegion")
    int[] getColorRegionsByCreatureSort(Creature creature);
}
