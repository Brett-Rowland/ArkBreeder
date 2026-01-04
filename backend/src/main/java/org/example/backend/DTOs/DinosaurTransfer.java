package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.Domains.DinoColors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DinosaurTransfer {
    private long dinoId;
    private List<StatsTransfer> stats;
    private List<DinoColors> colorRegions;
    private String dinosaurNickname;
}
