package org.example.backend.ValueObjects;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// This is a class that is mainly to help with all of the stats work

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stats {

//    Amount of points allocated to that specific stat
    private int statPoints;

//    Mutation Count for that specific stat
    private int mutationCount;
}
