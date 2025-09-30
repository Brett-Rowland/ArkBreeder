package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DinosaurTransfer {
    private String nickname;
    private int[][] colors;
    private int[][] stats;
}
