package org.example.backend.Repo;

import org.example.backend.Domains.Dinosaur;
import org.example.backend.Domains.DinosaurStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DinosaurStatsRepo.
 *
 * Repository interface for managing {@link DinosaurStats} entities.
 *
 * This repository provides access to per-dinosaur stat records,
 * which represent stat point allocations for individual dinosaurs.
 *
 * Queries defined here ensure stat data is retrieved in a consistent
 * and predictable order, which is critical for computation and display.
 *
 * Notes:
 * - Dinosaur stats are instance-specific and differ from base stats.
 * - Ordering by stat type ensures deterministic processing.
 */
@Repository
public interface DinosaurStatsRepo extends JpaRepository<DinosaurStats, Integer> {

    /**
     * Retrieves all stat entries for a specific dinosaur,
     * ordered by stat type.
     *
     * This ordering is required to ensure consistent
     * computation and UI display.
     *
     * @param dino dinosaur entity
     * @return ordered list of {@link DinosaurStats} records
     */
    @Query("SELECT ds FROM DinosaurStats ds WHERE ds.dinosaur = ?1 ORDER BY ds.stats.statType ASC")
    List<DinosaurStats> getDinosaurStatsASC(Dinosaur dino);
}
