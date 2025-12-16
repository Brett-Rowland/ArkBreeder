package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    @JsonManagedReference("creature-line")
    private List<BreedingLine> breedingLines;

    @OneToMany(mappedBy = "creature", fetch = FetchType.LAZY)
    @JsonManagedReference("creature-regions")
    private List<ColorRegions> colorRegions;


    @OneToMany(mappedBy = "creature", fetch = FetchType.LAZY)
    @JsonManagedReference("creature-base")
    private List<BaseStats> baseStats;

    @Column()
    private Date last_updated =  new Date();


    @Column()
    private boolean validated = false;

}
