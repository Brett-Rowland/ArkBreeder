package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BreedingLineDTO.
 *
 * Data Transfer Object representing a fully computed view of a breeding line.
 *
 * This DTO is used to return all information required to display and
 * evaluate a breeding line, including its associated creature, color regions,
 * computed stat values, and dinosaur calculation results.
 *
 * This object is intended for read/display purposes and does not
 * represent a persistent domain entity.
 *
 * Contents:
 * - creatureName: name of the creature species being bred
 * - breedingLineId: unique identifier of the breeding line
 * - colorRegions: color region metadata used for display
 * - maxStats: computed maximum stat values for the breeding line
 * - dinosaurCalc: computed dinosaur values for the line
 * - breedingLineNickname: display name of the breeding line
 *
 * Notes:
 * - This DTO aggregates both reference data and computed results.
 * - Values within this DTO are derived from domain entities and
 *   ComputationService outputs.
 * - This DTO may evolve as breeding line views become more detailed.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedingLineDTO {

    /**
     * Name of the creature species associated with the breeding line.
     */
    private String creatureName;

    /**
     * Unique identifier for the breeding line.
     */
    private long breedingLineId;

    /**
     * Color region metadata for the creature species.
     */
    private List<DinosaurColorRegionDTO> colorRegions;

    /**
     * Computed maximum stat values for the breeding line.
     */
    private List<StatsDTO> maxStats;

    /**
     * Computed dinosaur calculation results for this breeding line.
     */
    private List<DinosaurDTO> dinosaurCalc;

    /**
     * Display nickname for the breeding line.
     */
    private String breedingLineNickname;
}
