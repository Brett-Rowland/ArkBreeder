package org.example.backend.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ColorRegionTransfer {
    private int colorRegion;
    private String hexCode;
    private String colorName;

    public ColorRegionTransfer(int colorRegion,  String colorName, String hexCode){
        this.colorRegion = colorRegion;
        this.colorName = colorName;
        this.hexCode = hexCode;
    }


}
