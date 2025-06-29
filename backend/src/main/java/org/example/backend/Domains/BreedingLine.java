package org.example.backend.Domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "breeding_line")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BreedingLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long breedingLineId;


    @Column(length = 127)
    private String lineName;

//    Need connectors with all of this. Species ID, User ID and Preset ID





}
