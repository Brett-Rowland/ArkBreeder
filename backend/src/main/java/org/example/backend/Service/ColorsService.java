package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.ArkColors;
import org.example.backend.Repo.ArkColorsRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ColorsService.
 *
 * Service layer responsible for managing Ark color reference data.
 *
 * This service provides basic CRUD operations for {@link ArkColors},
 * which represent in-game Ark color definitions (ID, name, hex value).
 *
 * Notes:
 * - Ark colors are treated as reference/static data.
 * - This service performs minimal logic and delegates persistence
 *   operations directly to {@link ArkColorsRepo}.
 */
@Service
@AllArgsConstructor
public class ColorsService {

    /** Repository for Ark color persistence and lookup. */
    private final ArkColorsRepo arkColorsRepo;

    /**
     * Updates an existing Ark color definition.
     *
     * Persisted effects:
     * - updates the embedded color value (name and hex code)
     *   for the specified color ID.
     *
     * @param arkColor new color values to apply
     * @param colorId in-game Ark color ID
     * @throws RuntimeException if the color does not exist
     */
    public void updateColor(ArkColors arkColor, long colorId) {
        ArkColors oldColor = arkColorsRepo.getArkColorsByColorId(colorId);

        if (oldColor == null) {
            throw new RuntimeException("Color with id " + colorId + " not found");
        }

        oldColor.setColor(arkColor.getColor());
        arkColorsRepo.save(oldColor);
    }

    /**
     * Deletes an Ark color definition by ID.
     *
     * Persisted effects:
     * - removes the color record from persistence.
     *
     * @param colorId in-game Ark color ID
     */
    public void deleteColor(long colorId) {
        arkColorsRepo.deleteArkColorsByColorId(colorId);
    }

    /**
     * Retrieves all Ark color definitions.
     *
     * @return list of {@link ArkColors} records
     */
    public List<ArkColors> getAllColors() {
        return arkColorsRepo.findAll();
    }

    /**
     * Creates multiple Ark color definitions in bulk.
     *
     * Persisted effects:
     * - saves all provided color records.
     *
     * @param arkColors list of Ark color definitions to create
     */
    public void createColorList(List<ArkColors> arkColors) {
        arkColorsRepo.saveAll(arkColors);
    }
}
