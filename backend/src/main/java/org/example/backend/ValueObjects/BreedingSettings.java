package org.example.backend.ValueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedingSettings {

    @Column(name = "health_scale_factor")
    private Float healthScaleFactor;

    @Column(name = "stamina_scale_factor")
    private Float staminaScaleFactor;

    @Column(name = "oxygen_scale_factor")
    private Float oxygenScaleFactor;

    @Column(name = "food_scale_factor")
    private Float foodScaleFactor;

    @Column(name = "weight_scale_factor")
    private Float weightScaleFactor;

    @Column(name = "melee_scale_factor")
    private Float meleeScaleFactor;

    @Column(name = "health_additive")
    private Float healthAdditive;

    @Column(name = "health_multiplicand")
    private Float healthAffinity;

    @Column (name = "food_multiplicand")
    private Float foodAffinity;

    @Column(name = "melee_additive")
    private Float meleeAdditive;

    @Column(name = "melee_multiplicand")
    private Float meleeAffinity;

}
