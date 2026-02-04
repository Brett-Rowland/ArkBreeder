package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Stats;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseStatsDTO {
    public Stats.STATS stats;
    public float baseValue;
    public float incrementPerPoint;
    public float statAdditive;
    public float statMultiplicand;
}
