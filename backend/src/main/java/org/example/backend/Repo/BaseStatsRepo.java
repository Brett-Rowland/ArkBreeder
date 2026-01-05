package org.example.backend.Repo;

import org.example.backend.DTOs.StatsDTO;
import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BaseStatsRepo.
 *
 * Repository interface for managing {@link BaseStats} entities.
 *
 * This repository provides read access to predefined base stat values
 * associated with creature species. These values act as static reference
 * data and are used as inputs for all stat computations.
 *
 * Queries defined here focus on retrieving base stats in a consistent
 * and predictable order based on stat type.
 *
 * Notes:
 * - Base stats are treated as reference data and are not frequently modified.
 * - Ordering by stat type ensures deterministic computation and display.
 */
@Repository
public interface BaseStatsRepo extends JpaRepository<BaseStats, Integer> {

    /**
     * Retrieves base stats for a specific creature species, ordered by stat type.
     *
     * @param creature creature species entity
     * @return ordered list of {@link BaseStats} records
     */
    @Query("SELECT bs FROM BaseStats bs WHERE bs.creature = ?1 ORDER BY bs.stats.statType ASC")
    List<BaseStats> getBaseStatsASC(Creature creature);

    /**
     * Retrieves base stats for a creature species by ID, ordered by stat type.
     *
     * @param creatureId unique identifier of the creature species
     * @return ordered list of {@link BaseStats} records
     */
    @Query("SELECT bs FROM BaseStats bs WHERE bs.creature.creatureId = ?1 ORDER BY bs.stats.statType ASC")
    List<BaseStats> getBaseStatsASCById(long creatureId);

    /**
     * Retrieves only stat types (as {@link StatsDTO}) for a creature species,
     * ordered by stat type.
     *
     * This query is primarily used during validation and initialization
     * workflows where only stat categories are required, not full base stat values.
     *
     * @param creatureId unique identifier of the creature species
     * @return ordered list of {@link StatsDTO} containing stat types
     */
    @Query(
            "SELECT new org.example.backend.DTOs.StatsDTO(bs.stats.statType) " +
                    "FROM BaseStats bs " +
                    "WHERE bs.creature.creatureId = ?1 " +
                    "ORDER BY bs.stats.statType ASC"
    )
    List<StatsDTO> getStatTypeASCByID(long creatureId);
}
