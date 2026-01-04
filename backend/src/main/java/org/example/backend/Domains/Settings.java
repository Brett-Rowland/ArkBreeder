package org.example.backend.Domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.BreedingSettings;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Servers server;

    @Column(name = "maturing_rate")
    private Float maturingRate;

    @Column(name = "egg_hatch_rate")
    private Float eggHatchRate;

    @Column(name = "taming_rate")
    private Float tamingRate;

    @Column(name = "single_player")
    private Boolean singlePlayer;

    @Column(name = "event")
    private Boolean event;

    @Column(nullable = false)
    private BreedingSettings breedingSettings;
}
