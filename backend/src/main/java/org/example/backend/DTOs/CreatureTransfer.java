package org.example.backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatureTransfer {
    private long creatureId;
    private String creatureName;
    private List<StatsTransfer> stats;
    private Long colorRegionTotal;


    CreatureTransfer(long creatureId, String creatureName, Long colorRegionTotal) {
        this.creatureId = creatureId;
        this.creatureName = creatureName;
        this.colorRegionTotal = colorRegionTotal;
    }
}
