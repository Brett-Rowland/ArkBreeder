package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "creature_color_region")
@AllArgsConstructor
@NoArgsConstructor
public class ColorRegions {
    public enum Visibility {
        VISIBLE_COLOR,
        VISIBLE_DATA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DCRId;

    @ManyToOne
    @JoinColumn(name = "creature_id")
    @JsonBackReference("creature-regions")
    @ToString.Exclude
    private Creature creature;

    @Column(name = "color_region")
    private int colorRegion;

    @Column(name = "region_descriptor", length = (127))
    private String regionDescriptor;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private Visibility visibility = Visibility.VISIBLE_COLOR;
}
