package org.example.backend.Domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Color;

@Data
@Table(name = "ark_colors")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ArkColors {

    @Id
    private String id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="colorName", column = @Column(name = "color_name")),
            @AttributeOverride(name="colorHex", column = @Column(name = "color_hex"))
    })
    private Color color;
}
