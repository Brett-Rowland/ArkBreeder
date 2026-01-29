package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Stats;

@Entity
@Table(name = "breeding_settings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BreedingSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stat_type")
    private Stats.STATS stats;

    @Column(name = "wild_scale")
    private float wildScale;

    @Column(name = "stat_additive")
    private float statAdditive;

    @Column(name = "stat_affinity")
    private float statAffinity;

    @ManyToOne
    @JsonBackReference
    private Servers server;
}
