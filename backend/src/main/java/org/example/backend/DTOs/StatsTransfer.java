package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Stats;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsTransfer {
    private Stats.STATS statType;
    private int totalPoints;
    private float calcTotal;

    public StatsTransfer(Stats.STATS statType, int totalPoints){
        this.statType = statType;
        this.totalPoints = totalPoints;
    }

    public StatsTransfer(Stats.STATS statType){
        this.statType = statType;
    }
}
