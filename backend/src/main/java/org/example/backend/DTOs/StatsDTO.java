package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Stats;

/**
 * StatsDTO.
 *
 * Data Transfer Object representing a stat entry and its associated values.
 *
 * This DTO is used across multiple workflows, including:
 * - creature validation
 * - dinosaur stat editing
 * - breeding line computation results
 *
 * It represents both raw point allocations and computed totals,
 * depending on how it is constructed and populated.
 *
 * Contents:
 * - statType: stat category (e.g., HEALTH, STAMINA, MELEE)
 * - totalPoints: total points allocated to this stat
 * - calcTotal: computed stat value after applying calculations
 *
 * Notes:
 * - Not all fields are required in all contexts.
 * - calcTotal is typically populated only after computation.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {

    /**
     * Stat category/type.
     */
    private Stats.STATS statType;

    /**
     * Total number of points allocated to this stat.
     */
    private int totalPoints;

    /**
     * Computed stat value after applying calculation logic.
     */
    private float calcTotal;

    /**
     * Constructor used when only stat type and point allocation are required.
     */
    public StatsDTO(Stats.STATS statType, int totalPoints) {
        this.statType = statType;
        this.totalPoints = totalPoints;
    }

    /**
     * Constructor used when only stat type is required.
     *
     * Commonly used during initialization or validation setup.
     */
    public StatsDTO(Stats.STATS statType) {
        this.statType = statType;
    }
}
