package org.example.backend.Domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "settings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settings_id")
    private Long settingsId;

    @Column(name = "maturing_rate")
    private Float maturingRate;

    @Column(name = "egg_hatch_rate")
    private Float eggHatchRate;

    @Column(name = "taming_rate")
    private Float tamingRate;

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

    @Column(name = "single_player")
    private Boolean singlePlayer;

    @Column(name = "event")
    private Boolean event;
}
