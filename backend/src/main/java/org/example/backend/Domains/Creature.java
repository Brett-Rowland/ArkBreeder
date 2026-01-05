package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;

import java.util.Date;
import java.util.List;

/**
 * Creature table.
 *
 * Represents a base creature  definition (e.g., Rex, Therizino).
 *
 * A Creature acts as the foundational reference for:
 * - base stat definitions
 * - color region definitions
 * - breeding line creation
 *
 * Creature records define what *can* exist, not individual dinosaurs.
 * Individual dinosaur instances are modeled separately.
 *
 * Primary key:
 * - creatureId: generated identifier for the creature 
 *
 * Relationships:
 * - breedingLines (One-to-Many): breeding lines associated with this {@link BreedingLine}
 * - colorRegions (One-to-Many): color region definitions for the {@link ColorRegions}
 * - baseStats (One-to-Many): predefined base stat values for the {@link BaseStats}
 *
 * Key fields:
 * - creatureName: display name of the creature 
 * - validated: indicates whether the creature data has been verified
 * - lastUpdated: timestamp of the last modification to this creature
 *
 * Constraints / rules:
 * - Creature data is treated as reference/configuration data
 * - A creature may have multiple breeding lines
 * - Base stats and color regions are defined at the  level
 *
 * Notes:
 * - Creature records are validated through a manual validation workflow
 * - Derived stat values are not stored on the creature; they are computed dynamically
 */
@Table(name = "creature_base")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creature {

    /**
     * Primary key for the creature .
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long creatureId;

    /**
     * Display name of the creature  (e.g., "Therizino").
     */
    @Column(length = 127)
    private String creatureName;

    /**
     * Breeding lines associated with this creature .
     */
    @OneToMany(mappedBy = "creature")
    @JsonManagedReference("creature-line")
    private List<BreedingLine> breedingLines;

    /**
     * Color region definitions for this creature .
     *
     * Defines which color regions exist and how they are labeled/visible.
     */
    @OneToMany(mappedBy = "creature", fetch = FetchType.LAZY)
    @JsonManagedReference("creature-regions")
    private List<ColorRegions> colorRegions;

    /**
     * Base stat definitions for this creature .
     *
     * Base stats define the starting values for stat calculations
     * before any levels, mutations, imprinting, or server multipliers.
     */
    @OneToMany(mappedBy = "creature")
    @JsonManagedReference("creature-base")
    @OrderBy("stats.statType ASC")
    private List<BaseStats> baseStats;

    /**
     * Timestamp of the last update to this creature record.
     */
    @Column
    private Date last_updated = new Date();

    /**
     * Validation flag indicating whether this creature's data
     * has been verified and approved for use.
     */
    @Column
    private boolean validated = false;
}
