package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Dinosaur table.
 *
 * Represents an individual dinosaur instance within a breeding line.
 *
 * A Dinosaur holds the raw, per-instance data required to compute final stat values,
 * such as stat point distributions, taming effectiveness, and color selections.
 *
 * This entity does NOT store base creature stats or derived/computed values.
 * All computed values are calculated dynamically via ComputationService.
 *
 * Primary key:
 * - dinoId: generated identifier for the dinosaur
 *
 * Relationships:
 * - breedingLine (Many-to-One): breeding line this dinosaur belongs to {@link BreedingLine}
 * - dinoColors (One-to-Many): per-region color assignments for this dinosaur {@link DinoColors}
 * - dinosaurStats (One-to-Many): raw stat point allocations for this dinosaur {@link DinosaurStats}
 *
 * Key fields:
 * - dinosaurNickname: display name for the dinosaur
 * - gender: biological role used for breeding logic
 * - tamingEffectiveness: taming effectiveness value used in stat calculations
 * - deleted: soft delete flag
 *
 * Constraints / rules:
 * - A dinosaur must belong to exactly one breeding line
 * - deleted = true hides the dinosaur from normal queries
 *
 * Notes:
 * - Maewings are treated as a special breeding case and may breed regardless of gender.
 * - Base stats are sourced from the associated Creature via the breeding line.
 * - Final stat values are computed dynamically and not persisted.
 */
@Data
@Table(name = "dinosaur")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dinosaur {

    /**
     * Gender/breeding role of the dinosaur.
     *
     * MAEWING represents a special case that may breed regardless of gender.
     */
    public enum Gender {
        MALE,
        FEMALE,
        MAEWING
    }

    /**
     * Primary key for the dinosaur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dino_id")
    private Long dinoId;

    /**
     * Display nickname for the dinosaur.
     */
    @Column(length = 127, name = "dinosaur_nickname")
    private String dinosaurNickname;

    /**
     * Gender or breeding role of the dinosaur.
     */
    @Column(length = 127)
    private Gender gender = Gender.MALE;

    /**
     * Breeding line this dinosaur belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "breeding_line_id")
    @JsonBackReference("dino-line")
    private BreedingLine breedingLineId;

    /**
     * Color assignments for this dinosaur by region.
     */
    @OneToMany(mappedBy = "dinosaur")
    @JsonManagedReference("dino-colors")
    private List<DinoColors> dinoColors;

    /**
     * Raw stat point allocations for this dinosaur.
     *
     * These values are used as inputs for stat calculations.
     */
    @OneToMany(mappedBy = "dinosaur", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<DinosaurStats> dinosaurStats;

    /**
     * Taming effectiveness value applied during stat calculations.
     */
    @Column(name = "taming_effect")
    private float tamingEffectiveness;

    /**
     * Imprint value applied during stat calculations
     * */
    @Column(name = "imprint")
    private float imprint;

    /**
     * Soft delete flag. True indicates this dinosaur is inactive/deleted.
     */
    private boolean deleted = false;
}
