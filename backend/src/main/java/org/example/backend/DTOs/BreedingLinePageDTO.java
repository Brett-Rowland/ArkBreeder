package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * BreedingLinePageDTO.
 *
 * Data Transfer Object used to populate the Breeding Lines page.
 *
 * This DTO aggregates multiple datasets required by the frontend
 * to render the breeding lines view without making multiple API calls.
 *
 * It is intended as a convenience/response wrapper rather than a
 * persistent domain object.
 *
 * Contents:
 * - breedingLines: list of breeding line summaries
 * - settingsList: list of available server settings
 * - creatureList: list of available creature species
 *
 * Notes:
 * - This DTO exists to simplify frontend page initialization.
 * - Some fields may be redundant in the future as the API evolves.
 * - DTOs are intentionally flexible and may change independently
 *   of domain entities.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BreedingLinePageDTO {

    /**
     * Breeding line summaries displayed on the page.
     */
    List<BreedingLineDTO> breedingLines = new ArrayList<>();

    /**
     * List of server settings available to the user.
     */
    List<SettingsDTO> settingsList = new ArrayList<>();

    /**
     * List of creature species available for breeding lines.
     */
    List<ValidationCreatureDTO> creatureList = new ArrayList<>();
}
