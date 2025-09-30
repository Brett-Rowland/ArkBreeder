package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table (name = "creature_base")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Creature {

    enum TYPE {
        LAND, AQUATIC, FLYER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long creatureId;

    @Column(length = 127)
    private String creatureName;

    @Column
    private TYPE type;

    @OneToMany(mappedBy = "creature")
    @JsonManagedReference("creature-line")
    private List<BreedingLine> breedingLines;

    @OneToMany(mappedBy = "creature")
    @JsonManagedReference("creature-regions")
    private List<ColorRegions> colorRegions;


    @OneToMany(mappedBy = "creature")
    @JsonManagedReference("creature-base")
    private List<BaseStats> baseStats;
}
