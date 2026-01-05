package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.backend.ValueObjects.StatPoints;

/**
 * DinosaurStats table.
 *
 * Represents the raw stat point distribution for an individual dinosaur.
 *
 * This entity stores the point allocations used as inputs for computation
 * (e.g., wild points, mutation points, or any other point categories your
 * StatPoints value object models).
 *
 * This table does NOT store derived/computed stat values. Final stat values
 * are computed dynamically using:
 * - Creature base stats
 * - Server settings
 * - Dinosaur-specific inputs (including these point allocations)
 *
 * Primary key:
 * - id: generated identifier for the dinosaur stat record
 *
 * Relationships:
 * - dinosaur (Many-to-One): dinosaur this stat distribution belongs to
 *
 * Key fields:
 * - stats: embedded {@link StatPoints} value object representing point allocations
 *
 * Constraints / rules:
 * - A dinosaur may have multiple DinosaurStats records depending on how StatPoints
 *   is modeled (e.g., one row per stat type) OR it may represent a full set of points.
 * - Records are treated as input data for computation and should remain consistent
 *   with the creature's supported stat types.
 *
 * Notes:
 * - This entity exists to persist the "raw" point inputs so computed values can be
 *   recalculated at any time without loss of accuracy.
 */
@Table(name = "dinosaur_stats")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DinosaurStats {

    /**
     * Primary key for the dinosaur stats record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Dinosaur this stat allocation belongs to.
     * {@link Dinosaur}
     */
    @ManyToOne
    @JoinColumn(name = "dinoId")
    @JsonBackReference
    @ToString.Exclude
    private Dinosaur dinosaur;

    /**
     * Embedded stat point allocations for the dinosaur.
     * {@link StatPoints}
     */
    @Embedded
    private StatPoints stats;
}
