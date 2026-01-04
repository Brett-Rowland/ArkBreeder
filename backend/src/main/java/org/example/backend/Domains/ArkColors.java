package org.example.backend.Domains;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Color;

import java.util.List;

@Data
@Table(name = "ark_colors")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ArkColors {

    @Id
    @Column(name = "color_id")
    private long colorId;


    @OneToMany(mappedBy = "arkColor")
    @JsonManagedReference("color-dinosaur")
    private List<DinoColors> dinoColors;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "colorName", column = @Column(name = "color_name")),
            @AttributeOverride(name = "colorHex", column = @Column(name = "color_hex"))
    })
    private Color color;
}