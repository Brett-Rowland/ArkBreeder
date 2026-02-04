package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DinosaurPageDTO {

    List<DinosaurDTO> dinosaurDTOList;
    CreatureDTO creatureDTO;
    SettingsDTO settingsDTO;


}
