package org.example.backend.ValueObjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * StatPoints.
 *
 * Embeddable value object representing point allocations
 * for a specific stat on an individual dinosaur.
 *
 * This class extends {@link Stats} and adds per-dinosaur
 * allocation data that is not part of base creature definitions.
 *
 * Responsibilities:
 * - store wild stat points
 * - store mutation count per stat
 *
 * Design notes:
 * - Used within {@link org.example.backend.Domains.DinosaurStats}
 * - Inherits stat type and metadata from {@link Stats}
 * - Contains no calculation logic
 *
 * Calculation context:
 * - Total effective points are derived as:
 *   wildPoints + (mutationCount * 2)
 * - Final stat values are computed in {@link org.example.backend.Service.ComputationService}
 */
@EqualsAndHashCode(callSuper = true)
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatPoints extends Stats {

    /**
     * Number of wild stat points allocated to this stat.
     */
    private int statPoints;

    /**
     * Number of mutation points applied to this stat.
     *
     * Each mutation contributes two effective stat points.
     */
    private int mutationCount;
}
