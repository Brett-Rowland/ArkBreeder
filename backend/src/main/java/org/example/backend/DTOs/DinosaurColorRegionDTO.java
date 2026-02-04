package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ColorRegionDTO.
 *
 * Data Transfer Object representing a resolved color assignment
 * for a specific color region.
 *
 * This DTO is used to return color information in a frontend-friendly
 * format, combining the region index with the resolved Ark color
 * name and hex code.
 *
 * This object is intended strictly for display purposes and does not
 * represent a persistent domain entity.
 *
 * Contents:
 * - colorRegion: numeric Ark color region index (e.g., 0–5)
 * - colorName: human-readable name of the color
 * - hexCode: hexadecimal color value used for UI rendering
 *
 * Notes:
 * - This DTO typically represents the final resolved color value
 *   after joining region definitions with Ark color metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DinosaurColorRegionDTO {

    /**
     * Number representing what color it identifies in Ark Survival Ascended
     * */
    private long colorNumber;
    /**
     * Numeric Ark color region index (e.g., 0–5).
     */
    private int colorRegion;

    /**
     * Human-readable name of the color assigned to this region.
     */
    private String colorName;

    /**
     * Hexadecimal color value used for UI rendering.
     */
    private String hexCode;

    /**
     * Convenience constructor for creating a color region DTO
     * with all required display values.
     */
    public DinosaurColorRegionDTO(int colorRegion, String colorName, String hexCode) {
        this.colorRegion = colorRegion;
        this.colorName = colorName;
        this.hexCode = hexCode;
    }
}
