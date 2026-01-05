package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DinoColors table.
 *
 * Represents a color assignment for a specific dinosaur and color region.
 *
 * This entity acts as a join/association table between:
 * - {@link Dinosaur} (individual dinosaur instance)
 * - {@link ArkColors} (canonical Ark color definitions)
 *
 * Each record maps a single color region index on a dinosaur to
 * a specific Ark color.
 *
 * Primary key:
 * - dinoColorID: generated identifier for the dinosaur color record
 *
 * Relationships:
 * - arkColor (Many-to-One): the Ark color assigned to the region {@link ArkColors}
 * - dinosaur (Many-to-One): the dinosaur this color applies to {@link Dinosaur}
 *
 * Key fields:
 * - colorRegion: numeric Ark color region index (e.g., 0–5)
 *
 * Constraints / rules:
 * - A dinosaur may have multiple DinoColors records (one per region)
 * - Each (dinosaur, colorRegion) pair should be unique
 *
 * Notes:
 * - This entity stores per-dinosaur color values only.
 * - Color region definitions (labels, visibility) are stored at the Creature level.
 */
@Entity
@Table(name = "dino_colors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DinoColors {

    /**
     * Primary key for the dinosaur color record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dino_color_id")
    private Long dinoColorID;

    /**
     * Numeric Ark color region index (e.g., 0–5) this color applies to.
     */
    @Column(name = "color_region")
    private int colorRegion;

    /**
     * Ark color assigned to this region.
     */
    @ManyToOne
    @JoinColumn(name = "colorId")
    @JsonBackReference("color-dinosaur")
    private ArkColors arkColor;

    /**
     * Dinosaur this color assignment belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "dino_id")
    @JsonBackReference("dino-colors")
    private Dinosaur dinosaur;
}
