package org.example.backend.Repo;

import org.example.backend.Domains.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepo extends JpaRepository<Settings, Integer> {


    Settings getSettingsBySettingsId(Long settingsId);

    void deleteBySettingsId(Long SettingsId);
}
