package org.example.backend.ValueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BreedingSettings.
 *
 * Embeddable value object representing all server-side multipliers
 * and modifiers used during creature stat calculations.
 *
 * This object models Ark: Survival Ascended breeding, taming,
 * and stat scaling behavior and is applied consistently across:
 * - breeding line calculations
 * - individual dinosaur stat computation
 * - validation previews
 *
 * Design notes:
 * - Stored as an embedded object inside {@link org.example.backend.Domains.Settings}
 * - Contains no business logic; purely a configuration holder
 * - Values directly influence mathematical formulas in {@link org.example.backend.Service.ComputationService}
 *
 * Each field corresponds to an in-game server multiplier or affinity value.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedingSettings {

    /**
     * Multiplier applied to health stat increases per point.
     */
    @Column(name = "health_scale_factor")
    private Float healthScaleFactor;

    /**
     * Multiplier applied to stamina stat increases per point.
     */
    @Column(name = "stamina_scale_factor")
    private Float staminaScaleFactor;

    /**
     * Multiplier applied to oxygen stat increases per point.
     */
    @Column(name = "oxygen_scale_factor")
    private Float oxygenScaleFactor;

    /**
     * Multiplier applied to food stat increases per point.
     */
    @Column(name = "food_scale_factor")
    private Float foodScaleFactor;

    /**
     * Multiplier applied to weight stat increases per point.
     */
    @Column(name = "weight_scale_factor")
    private Float weightScaleFactor;

    /**
     * Multiplier applied to melee stat increases per point.
     */
    @Column(name = "melee_scale_factor")
    private Float meleeScaleFactor;

    /**
     * Additive bonus applied to health calculations.
     *
     * Typically used to model flat health bonuses from taming
     * or server-specific modifiers.
     */
    @Column(name = "health_additive")
    private Float healthAdditive;

    /**
     * Multiplicative affinity applied to health during taming.
     *
     * Represents how taming effectiveness influences health scaling.
     */
    @Column(name = "health_multiplicand")
    private Float healthAffinity;

    /**
     * Multiplicative affinity applied to food during taming.
     *
     * Used to model taming effectiveness bonuses on food stat.
     */
    @Column(name = "food_multiplicand")
    private Float foodAffinity;

    /**
     * Additive bonus applied to melee calculations.
     *
     * Represents flat melee increases applied during calculation.
     */
    @Column(name = "melee_additive")
    private Float meleeAdditive;

    /**
     * Multiplicative affinity applied to melee during taming.
     *
     * Used to scale melee damage based on taming effectiveness.
     */
    @Column(name = "melee_multiplicand")
    private Float meleeAffinity;
}
