package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.BreedingSettings;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SettingsTransfer {
    long settingsId;
    String settingsName;
    BreedingSettings breedingSettings;


    SettingsTransfer(long settingsId, String settingsName) {
        this.settingsId = settingsId;
        this.settingsName = settingsName;
    }

}
