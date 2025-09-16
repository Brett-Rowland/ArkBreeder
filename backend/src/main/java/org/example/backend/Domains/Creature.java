package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.StatsDefaults;

import java.util.List;

@Table (name = "creature_base")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long creatureId;

    @Column(length = 127)
    private String creatureName;

    @OneToMany(mappedBy = "creature")
    private List<BreedingLine> breedingLines;

    @OneToMany(mappedBy = "creature")
    @JsonManagedReference
    private List<ColorRegions> colorRegions;

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
