package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.ColorRegions;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatureDTO {
    private long creatureId;
    private String creatureName;
    private List<BaseStatsDTO> baseStatsDTOList;
    private List<CreatureRegionsDTO> colorRegionsList;

}
