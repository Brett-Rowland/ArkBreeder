package org.example.backend.Domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table("dino_colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DinoColors {

    @Column
    private int color_region;

}
