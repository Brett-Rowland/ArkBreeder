package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.BreedingSettings;

/**
 * SettingsDTO.
 *
 * Data Transfer Object representing a lightweight view of server settings.
 *
 * This DTO is primarily used to transfer server settings information
 * between backend and frontend without exposing the full Settings
 * domain entity.
 *
 * It supports both lightweight listings (ID + name) and full
 * configuration payloads depending on usage.
 *
 * Contents:
 * - settingsId: unique identifier of the settings record
 * - settingsName: display name associated with the settings/server
 * - breedingSettings: breeding-related configuration values
 *
 * Notes:
 * - This DTO is intended for transport and display purposes only.
 * - Not all fields are required in all contexts.
 * - Full computation logic is handled separately by services.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettingsDTO {

    /**
     * Unique identifier for the settings record.
     */
    long settingsId;

    /**
     * Display name associated with the settings configuration.
     */
    String settingsName;

    /**
     * Breeding-related configuration values.
     */
    BreedingSettings breedingSettings;

    /**
     * Constructor used for lightweight settings listings.
     *
     * Provides only the identifier and display name.
     */
    SettingsDTO(long settingsId, String settingsName) {
        this.settingsId = settingsId;
        this.settingsName = settingsName;
    }
}
