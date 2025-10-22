package org.example.backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreatureInput {

    String creatureName;
    String type;


//    Color Regions
    String [] colorRegionDescriptor;

//    This holds what color regions and color region Visibility
    int [][] colorRegions;

//    Base Stats
//    Base, Increment, Type
    int[][] stats;

}
