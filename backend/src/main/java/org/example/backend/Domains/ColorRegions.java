package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ColorRegions table.
 *
 * Represents a color region definition for a creature species.
 *
 * A color region defines a numbered region on a creature model (e.g., region 0–5 in Ark)
 * along with a human-readable descriptor and visibility behavior.
 *
 * Color regions are part of the Creature definition and describe:
 * - which regions exist for a species
 * - how they should be labeled in the UI
 * - whether the region is visually displayed as a color or only as data
 *
 * Primary key:
 * - DCRId: generated identifier for the color region record
 *
 * Relationships:
 * - creature (Many-to-One): the creature species this color region belongs to {@link Creature}
 *
 * Key fields:
 * - colorRegion: numeric region index used by Ark (e.g., 0–5)
 * - regionDescriptor: display label for the region (e.g., "Body", "Feathers")
 * - visibility: determines how the region should be presented in the UI
 *
 * Constraints / rules:
 * - Color regions are defined per creature species
 * - A creature may have multiple color regions
 *
 * Notes:
 * - This entity defines region metadata only.
 * - Actual color values applied to dinosaurs are stored separately at the Dinosaur level.
 */
@Data
@Entity
@Table(name = "creature_color_region")
@AllArgsConstructor
@NoArgsConstructor
public class ColorRegions {

    /**
     * Controls how a color region should be displayed.
     *
     * VISIBLE_COLOR: region is shown visually and accepts color assignment
     * VISIBLE_DATA: region is tracked but not visually displayed
     */
    public enum Visibility {
        VISIBLE_COLOR,
        VISIBLE_DATA
    }

    /**
     * Primary key for the creature color region record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long DCRId;

    /**
     * Creature species this color region belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "creature_id")
    @JsonBackReference("creature-regions")
    @ToString.Exclude
    private Creature creature;

    /**
     * Numeric Ark color region index (e.g., 0–5).
     */
    @Column(name = "color_region")
    private int colorRegion;

    /**
     * Human-readable label for the color region.
     */
    @Column(name = "region_descriptor", length = 127)
    private String regionDescriptor;

    /**
     * Visibility rule for how this region should be presented in the UI.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private Visibility visibility = Visibility.VISIBLE_COLOR;
}
