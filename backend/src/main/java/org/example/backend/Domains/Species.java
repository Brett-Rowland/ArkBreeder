package org.example.backend.Domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.StatsDefaults;

@Table (name = "dino_base_stats")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long speciesId;

    @Column(length = 127)
    private String speciesName;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name ="baseValue", column = @Column(name = "base_health")),
            @AttributeOverride(name= "incrementPerPoint", column = @Column(name = "increment_health"))
    })
    private StatsDefaults healthDefaults;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "baseValue", column = @Column(name = "base_stamina")),
            @AttributeOverride(name = "incrementPerPoint", column = @Column(name = "increment_stamina"))
    })
    private StatsDefaults staminaDefaults;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "baseValue", column = @Column(name = "base_oxygen")),
            @AttributeOverride(name = "incrementPerPoint", column = @Column(name = "increment_oxygen"))
    })
    private StatsDefaults oxygenDefaults;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "baseValue", column = @Column(name = "base_food")),
            @AttributeOverride(name = "incrementPerPoint", column = @Column(name = "increment_food"))
    })
    private StatsDefaults foodDefaults;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "baseValue", column = @Column(name = "base_melee")),
            @AttributeOverride(name = "incrementPerPoint", column = @Column(name = "increment_melee"))
    })
    private StatsDefaults meleeDefaults;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "baseValue", column = @Column(name = "base_weight")),
            @AttributeOverride(name = "incrementPerPoint", column = @Column(name = "increment_weight"))
    })
    private StatsDefaults weightDefaults;




}
