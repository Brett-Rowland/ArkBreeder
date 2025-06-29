package org.example.backend.ValueObjects;

import jakarta.persistence.Embeddable;

// Base values to keep those separate since those wont change to often

@Embeddable
public class StatsDefaults {
    //    Base Values in the game for specific stat
    private float baseValue;

    //    How much it gets added for each points put into it.
    private float incrementPerPoint;
}
