package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.backend.ValueObjects.StatPoints;
import org.example.backend.ValueObjects.Stats;

@Table(name = "dinosaur_stats")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DinosaurStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dinoId")
    @JsonBackReference
    @ToString.Exclude
    private Dinosaur dinosaur;


    @Embedded
    private StatPoints stats;
}
