package org.example.backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.Domains.DinoColors;

import java.util.List;

/**
 * DinosaurDTO.
 *
 * Data Transfer Object representing a computed dinosaur view.
 *
 * This DTO is primarily returned from computation workflows (ex: {@code dinoComputation})
 * and is intended to provide the frontend with a display-ready snapshot of a dinosaur.
 *
 * It contains both:
 * - point allocations (totalPoints)
 * - computed stat totals (calcTotal)
 * along with color assignments and basic dinosaur metadata.
 *
 * Contents:
 * - dinoId: unique identifier of the dinosaur
 * - stats: stat entries including total points and computed totals
 * - colorRegions: per-region color assignments for the dinosaur
 * - dinosaurNickname: display name of the dinosaur
 *
 * Notes:
 * - This DTO is not a persistent entity.
 * - Values are derived dynamically and are not stored directly in this object.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DinosaurDTO {

    /**
     * Unique identifier for the dinosaur.
     */
    private long dinoId;

    /**
     * Computed stat entries for the dinosaur.
     *
     * Each {@link StatsDTO} may include:
     * - statType
     * - totalPoints (wild + mutation*2)
     * - calcTotal (final computed stat)
     */
    private List<StatsDTO> stats;

    /**
     * Color assignments for this dinosaur by region.
     */
    private List<DinoColors> colorRegions;

    /**
     * Display nickname for the dinosaur.
     */
    private String dinosaurNickname;
}
