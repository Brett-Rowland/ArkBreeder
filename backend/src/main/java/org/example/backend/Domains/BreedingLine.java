package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "breeding_line")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BreedingLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breeding_line_id")
    private long breedingLineId;


    @Column(length = 127, name = "line_name")
    private String lineName;

//    Need connectors with all of this. Species ID, User ID and Preset ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;


    @OneToMany(mappedBy = "breedingLineId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JsonManagedReference("dino-line")
    private List<Dinosaur> dinosaurs;


    @ManyToOne
    @JoinColumn(name = "species_id")
    @JsonBackReference("creature-line")
    private Creature creature;



}
