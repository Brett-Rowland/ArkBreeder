package org.example.backend.ValueObjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Color.
 *
 * Embeddable value object representing an Ark color definition.
 *
 * This object encapsulates the display properties of a color used
 * throughout the application, including:
 * - human-readable color name
 * - hexadecimal color code for UI rendering
 *
 * Design notes:
 * - Embedded within {@link org.example.backend.Domains.ArkColors}
 * - Contains no identifiers or relationships
 * - Serves as a reusable, immutable-style data holder
 *
 * Usage:
 * - Provides consistent color representation across dinosaurs,
 *   breeding lines, and validation views.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Color {

    /**
     * Human-readable name of the color.
     */
    private String colorName;

    /**
     * Hexadecimal color code used for UI display.
     */
    private String colorHex;
}
