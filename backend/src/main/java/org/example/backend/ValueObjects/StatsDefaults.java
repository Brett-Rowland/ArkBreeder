package org.example.backend.ValueObjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * StatsDefaults.
 *
 * Embeddable value object representing the base stat configuration
 * for a creature species.
 *
 * This class defines the immutable, species-level stat values that
 * are consistent across all individuals of the same creature.
 *
 * Responsibilities:
 * - store base stat values
 * - define per-point stat scaling
 * - define additive and multiplicative modifiers
 *
 * Design notes:
 * - Extends {@link Stats} to inherit stat identity
 * - Used within {@link org.example.backend.Domains.BaseStats}
 * - Contains no computation logic
 *
 * Calculation context:
 * - Base values are combined with {@link StatPoints} and
 *   {@link BreedingSettings} during stat computation.
 * - Final stat math is handled exclusively in
 *   {@link org.example.backend.Service.ComputationService}.
 */
@EqualsAndHashCode(callSuper = true)
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDefaults extends Stats {

    /**
     * Base value for this stat as defined by the game.
     *
     * Example:
     * - Health base value
     * - Weight base value
     */
    private float baseValue;

    /**
     * Increment applied per stat point.
     *
     * Represents how much the stat increases
     * for each wild or mutation point.
     */
    private float incrementPerPoint;

    /**
     * Flat additive modifier applied during stat calculation.
     *
     * Used for stats that receive direct additive bonuses.
     */
    private float statAdditive = 0.0f;

    /**
     * Multiplicative modifier applied during stat calculation.
     *
     * Used to scale stat values based on taming effectiveness
     * or server-specific affinities.
     */
    private float statMultiplicand = 0.0f;
}
