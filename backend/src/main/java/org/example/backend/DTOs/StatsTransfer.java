package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.Domains.BaseStats;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsTransfer {
    private BaseStats.STATS statType;
    private int totalPoints;
    private float calcTotal;
}
