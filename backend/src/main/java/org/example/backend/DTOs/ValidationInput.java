package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.Domains.BreedingSettings;

import java.util.List;

/**
 * ValidationInput.
 *
 * Data Transfer Object used as input for creature and stat validation.
 *
 * This DTO packages all required inputs needed to perform validation
 * calculations without relying on persisted domain entities.
 *
 * It is primarily used during the creature validation workflow to
 * verify base stats, breeding configuration, and taming effectiveness.
 *
 * Contents:
 * - breedingSettings: breeding-related configuration values
 * - stats: stat values to be validated
 * - creatureId: identifier of the creature being validated
 * - tamingEffectiveness: taming effectiveness applied during validation
 *
 * Notes:
 * - This DTO is used only for validation and computation input.
 * - No values contained here are persisted directly.
 * - Validation results are derived dynamically via ComputationService.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationInput {

    /**
     * Breeding-related configuration values applied during validation.
     */
    private List<BreedingSettings> breedingSettings;

    /**
     * Stat values submitted for validation.
     */
    private List<StatsDTO> stats;

    /**
     * Identifier of the creature being validated.
     */
    private long creatureId;

    /**
     * Taming effectiveness value used in validation calculations.
     */
    private float tamingEffectiveness;
}
