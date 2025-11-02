package org.example.backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreatureInput {

    String creatureName;


//    Color Regions
    String [] colorRegionDescriptor;

//    This holds what color regions and color region Visibility
//    Example
//    [0,1]
//    Color Region 0, VISIBLE COLOR (From Color Regions ENUM)
    int [][] colorRegions;

//    Base Stats
//    [Base, Increment Percentage, Stat Additive, Stat Multiplicand, Stat Type]
    float[][] stats;


}
