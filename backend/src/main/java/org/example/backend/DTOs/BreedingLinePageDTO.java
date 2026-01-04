package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BreedingLinePageDTO {
    List<BreedingLineTransfer> breedingLines = new ArrayList<>();
    List<SettingsTransfer> settingsList = new ArrayList<>();
    List<CreatureTransfer> creatureList = new ArrayList<>();

}
