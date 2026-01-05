package org.example.backend.Domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.BreedingSettings;

/**
 * Settings table.
 *
 * Represents calculation and breeding configuration values applied to a server.
 *
 * Settings define how stat and breeding-related computations behave, including
 * maturation speed, egg hatch speed, taming rate, and special gameplay modifiers.
 *
 * This entity is tightly coupled to {@link Servers} and exists to separate
 * calculation parameters from server metadata.
 *
 * Primary key:
 * - settingsId: generated identifier for the settings record
 *
 * Relationships:
 * - server (One-to-One): server configuration this settings record belongs to ({@link Servers})
 *
 * Key fields:
 * - maturingRate: multiplier controlling creature maturation speed
 * - eggHatchRate: multiplier controlling egg incubation speed
 * - tamingRate: multiplier controlling taming speed
 * - singlePlayer: flag indicating single-player specific scaling rules
 * - event: flag indicating event-based multipliers are active
 * - breedingSettings: advanced breeding configuration values
 *
 * Constraints / rules:
 * - Each {@link Servers} record has exactly one Settings record
 * - Settings values are treated as input parameters for computation
 *
 * Notes:
 * - Settings directly influence all stat and breeding calculations
 *   performed for {@link Dinosaur} instances.
 * - This entity may be merged into {@link Servers} in a future refactor
 *   to simplify the domain model.
 */
@Entity
@Table(name = "settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings {

    /**
     * Primary key for the settings record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id")
    private Long settingsId;

    /**
     * Server this settings record is associated with.
     *
     * Each server owns exactly one Settings instance.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Servers server;

    /**
     * Multiplier controlling creature maturation speed.
     *
     * Higher values result in faster maturation.
     */
    @Column(name = "maturing_rate")
    private Float maturingRate;

    /**
     * Multiplier controlling egg incubation speed.
     *
     * Higher values result in faster egg hatching.
     */
    @Column(name = "egg_hatch_rate")
    private Float eggHatchRate;

    /**
     * Multiplier controlling taming speed.
     *
     * Higher values reduce the time required to tame creatures.
     */
    @Column(name = "taming_rate")
    private Float tamingRate;

    /**
     * Flag indicating whether single-player scaling rules are enabled.
     */
    @Column(name = "single_player")
    private Boolean singlePlayer;

    /**
     * Flag indicating whether event-based multipliers are active.
     *
     * Examples include holiday or special event boosts.
     */
    @Column(name = "event")
    private Boolean event;

    /**
     * Embedded breeding configuration values.
     *
     * Contains advanced breeding-related parameters such as
     * imprinting behavior, mating intervals, and other
     * breeding-specific rules.
     */
    @Column(nullable = false)
    private BreedingSettings breedingSettings;
}
