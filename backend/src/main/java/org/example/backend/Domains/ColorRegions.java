package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "dinosaur_color_region")
@AllArgsConstructor
@NoArgsConstructor
public class ColorRegions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DCRId;

    @ManyToOne
    @JoinColumn(name = "species_id")
    @JsonBackReference
    @ToString.Exclude
    private Creature creature;

    @Column(name = "color_region")
    private int colorRegion;

    @Column(name = "region_descriptor", length = (127))
    private String regionDescriptor;
}
