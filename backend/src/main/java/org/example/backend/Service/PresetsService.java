package org.example.backend.Service;

import org.example.backend.Domains.Presets;
import org.example.backend.Domains.Settings;
import org.example.backend.Domains.Users;
import org.example.backend.Repo.PresetsRepo;
import org.example.backend.Repo.SettingsRepo;
import org.example.backend.Repo.UsersRepo;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PresetsService {

    SettingsRepo settingsRepo;
    PresetsRepo presetsRepo;
    UsersRepo usersRepo;


    public Settings getSettings(Long settingsId) {
        return settingsRepo.getSettingsBySettingsId(settingsId);
    }

    public List<Presets> getPresets(Long token) {
        Users user = usersRepo.getUsersByToken(token);
        return presetsRepo.getPresetsByUser(user);

    }

    public void deleteSettings(Long presetID) {
        Presets preset = presetsRepo.getPresetsByPresetID(presetID);
        Long settingsId =  preset.getSettings().getSettingsId();
        Settings settings = settingsRepo.getSettingsBySettingsId(settingsId);
        settingsRepo.delete(settings);
        presetsRepo.delete(preset);

    }

    public void updatePreset(Long presetID, Settings settings, String presetName) {
        Presets preset = presetsRepo.getPresetsByPresetID(presetID);
        Long oldSettingsId = preset.getSettings().getSettingsId();

        preset.setSettings(settings);
        preset.setPresetName(presetName);
        presetsRepo.save(preset);
        settingsRepo.deleteBySettingsId(oldSettingsId); // clean up old one

    }
}
