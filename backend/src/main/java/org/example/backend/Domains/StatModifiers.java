package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "stat_modifiers")
public class StatModifiers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long statModId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="stats")
    private BaseStats.STATS stats;

    @Column(name = "stat_additive")
    private Float statAdditive;

    @Column(name = "stat_multiplicand")
    private Float statMultiplicand;

    @ManyToOne
    @JoinColumn(name = "statModifiers")
    @JsonBackReference("creature-stat-mods")
    private Creature creature;





}
