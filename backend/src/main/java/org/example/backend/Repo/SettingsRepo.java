package org.example.backend.Repo;

import org.example.backend.Domains.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SettingsRepo.
 *
 * Repository interface for managing {@link Settings} entities.
 *
 * This repository provides data access operations for server
 * configuration settings, including creation, retrieval,
 * update, and deletion.
 *
 * Notes:
 * - Settings entities are typically accessed through their
 *   associated {@link org.example.backend.Domains.Servers}.
 * - Standard {@link JpaRepository} operations are sufficient
 *   for current use cases.
 */
@Repository
public interface SettingsRepo extends JpaRepository<Settings, Integer> {

    /**
     * Retrieves a settings record by its unique identifier.
     *
     * @param settingsId unique identifier of the settings record
     * @return matching {@link Settings} entity
     */
    Settings getSettingsBySettingsId(Long settingsId);

    /**
     * Deletes a settings record by its unique identifier.
     *
     * @param SettingsId unique identifier of the settings record
     */
    void deleteBySettingsId(Long SettingsId);
}
