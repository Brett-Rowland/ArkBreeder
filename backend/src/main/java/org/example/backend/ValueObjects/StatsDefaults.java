package org.example.backend.ValueObjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// Base values to keep those separate since those wont change to often


@EqualsAndHashCode(callSuper = true)
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatsDefaults extends Stats{
    //    Base Values in the game for specific stat
    private float baseValue;

    //    How much it gets added for each points put into it.
    private float incrementPerPoint;

    private float statAdditive = 0.0f;

    private float statMultiplicand = 0.0f;
}
