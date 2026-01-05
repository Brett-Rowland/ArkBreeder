package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.backend.ValueObjects.StatsDefaults;

/**
 * BaseStats table.
 *
 * Represents the predefined base stat values for a creature species.
 *
 * Base stats define the starting values for each stat type (e.g., Health,
 * Stamina, Weight, Melee) before any wild levels, taming effectiveness,
 * imprinting, mutations, or server multipliers are applied.
 *
 * These values are used as the foundation for all stat computations
 * performed by the application.
 *
 * Primary key:
 * - id: generated identifier for the base stat record
 *
 * Relationships:
 * - creature (Many-to-One): the creature species these base stats belong to {@link Creature}
 *
 * Key fields:
 * - stats: embedded base stat values per stat type {@link StatsDefaults}
 *
 * Constraints / rules:
 * - Each creature has exactly one BaseStats record
 * - Base stat values are static reference data and not computed
 *
 * Notes:
 * - Derived stat values are computed dynamically via ComputationService.
 * - Base stats should only change if Ark balance values change.
 */
@Table(name = "base_stats")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseStats {

    /**
     * Primary key for the base stats record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Creature species this base stat set belongs to.
     *
     * Each creature is expected to have a single associated BaseStats record.
     */
    @ManyToOne
    @JoinColumn(name = "creatureId")
    @JsonBackReference("creature-base")
    @ToString.Exclude
    private Creature creature;

    /**
     * Embedded base stat values.
     *
     * Contains default stat values for each supported stat type
     * (e.g., Health, Stamina, Oxygen, Food, Weight, Melee).
     */
    @Embedded
    private StatsDefaults stats;
}
