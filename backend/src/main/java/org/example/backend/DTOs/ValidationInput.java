package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.BreedingSettings;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationInput {
    private BreedingSettings breedingSettings;
    private List<StatsTransfer> stats;
    private long creatureId;
    private float tamingEffectiveness;
}
