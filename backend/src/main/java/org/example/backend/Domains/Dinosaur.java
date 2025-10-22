package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/* This class holds basic dinosaur characteristics from each breeding lines. So this holds each personal dino
    Does not hold any of the base stats or other things like that.
*/
@Data
@Table (name = "dinosaur")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dinosaur {

    public enum Gender {
        MALE,
        FEMALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dino_id")
    private Long dinoId;

    @Column (length = 127)
    private String dinosaurNickname;

    @Column (length = 127)
    private Gender gender =  Gender.MALE;

    @ManyToOne
    @JoinColumn(name = "breeding_line_id")
    @JsonBackReference("dino-line")
    private BreedingLine breedingLineId;

    @OneToMany(mappedBy = "dinosaur")
    @JsonManagedReference("dino-colors")
    private List<DinoColors> dinoColors;

    @OneToMany(mappedBy = "dinosaur")
    @JsonManagedReference
    private List<DinosaurStats> dinosaurStats;

    @Column()
    private boolean tamed = true;

    @Column(name = "taming_effect")
    private float tamingEffectiveness;

    public void sortStats(){
        List<DinosaurStats> tempStats = dinosaurStats;

//        for (int i = 0; i<tempStats.size()-1; i++){
//            if (BaseStats.STATS.valueOf(String.valueOf(tempStats.get(i).getStats())) > temp){
//
//            }
//
//
//
//
//        }
    }
}
