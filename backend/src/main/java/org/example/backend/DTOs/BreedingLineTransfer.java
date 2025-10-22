package org.example.backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedingLineTransfer {

    private String creatureName;
    private long breedingLineId;
    private List<StatsTransfer> maxStats;
    private List<DinosaurTransfer> dinosaurCalc;
    private String breedingLineNickname;

}
