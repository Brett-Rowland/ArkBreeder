package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name  =  "dino_colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DinoColors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dinoColorID;

    @Column
    private int colorRegion;

    @ManyToOne
    @JoinColumn(name = "colorId")
    @JsonBackReference("color-dinosaur")
    private ArkColors arkColor;

    @ManyToOne
    @JoinColumn(name = "dino_id")
    @JsonBackReference("dino-colors")
    private Dinosaur dinosaur;
}
