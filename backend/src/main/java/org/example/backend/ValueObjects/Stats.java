package org.example.backend.ValueObjects;


import jakarta.persistence.Embeddable;

// This is a class that is mainly to help with all of the stats work

@Embeddable
public class Stats {

//    Amount of points allocated to that specific stat
    private int statPoints;

//    Mutation Count for that specific stat
    private int mutationCount;
}
