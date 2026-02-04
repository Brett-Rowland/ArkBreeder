package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ValidationCreatureDTO.
 *
 * Data Transfer Object used primarily for creature validation workflows.
 *
 * This DTO represents a lightweight view of a creature species, exposing
 * only the information required to validate base stats and color regions
 * without loading full domain relationships.
 *
 * This object is intentionally minimal and is not a persistent entity.
 *
 * Contents:
 * - creatureId: unique identifier of the creature species
 * - creatureName: display name of the creature
 * - stats: stat definitions used during validation
 * - colorRegionTotal: number of color regions defined for the creature
 *
 * Notes:
 * - This DTO is used during validation to ensure creature data is complete
 *   and accurate before being marked as validated.
 * - Some fields may be omitted depending on the validation stage.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationCreatureDTO {

    /**
     * Unique identifier for the creature species.
     */
    private long creatureId;

    /**
     * Display name of the creature species.
     */
    private String creatureName;

    /**
     * Stat definitions used during validation.
     */
    private List<StatsDTO> stats;

    /**
     * Total number of color regions defined for this creature.
     */
    private Long colorRegionTotal;

    /**
     * Constructor used for lightweight creature listings.
     *
     * Used when only ID and name are required.
     */
    ValidationCreatureDTO(long creatureId, String creatureName) {
        this.creatureId = creatureId;
        this.creatureName = creatureName;
    }

    /**
     * Constructor used during validation to include color region counts.
     */
    ValidationCreatureDTO(long creatureId, String creatureName, Long colorRegionTotal) {
        this.creatureId = creatureId;
        this.creatureName = creatureName;
        this.colorRegionTotal = colorRegionTotal;
    }
}
