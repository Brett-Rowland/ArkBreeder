package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * BreedingLine table.
 *
 * Represents a breeding lineage/group used to organize dinosaurs that are bred together
 * to optimize stats and/or colors over multiple generations.
 *
 * A breeding line provides the "context" for:
 * - which creature species is being bred (Creature)
 * - which server settings are applied for computations (Servers)
 * - which user owns and manages the line (Users)
 * - which dinosaur instances belong to the line (Dinosaur)
 *
 * Primary key:
 * - breedingLineId: generated identifier for the breeding line
 *
 * Relationships:
 * - user (Many-to-One): owner of the breeding line {@link Users}
 * - creature (Many-to-One): species definition tied to the line {@link Creature}
 * - server (Many-to-One): server configuration applied to computations for this line {@link Servers}
 * - dinosaurs (One-to-Many): all dinosaur instances associated with this line {@link Dinosaur}
 *
 * Key fields:
 * - lineName: display name for the breeding line
 * - deleted: soft delete flag (true indicates the line is hidden/inactive)
 *
 * Constraints / rules:
 * - lineName should be provided and is intended for display/UI
 * - deleted is used for soft deletion; records may remain in the database
 *
 * Notes:
 * - Stat calculations are not persisted directly on the BreedingLine; computed values are
 *   derived from Creature base stats, dinosaur inputs, and server settings.
 */
@Data
@Table(name = "breeding_line")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BreedingLine {

    /**
     * Primary key for the breeding line.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "breeding_line_id")
    private long breedingLineId;

    /**
     * Display name for the breeding line.
     */
    @Column(length = 127, name = "line_name")
    private String lineName;

    /**
     * User who owns this breeding line.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;

    /**
     * Dinosaurs that belong to this breeding line.
     *
     * Cascade/orphanRemoval ensures dinosaurs are managed alongside the line,
     * while fetch is lazy to avoid loading the full list unless requested.
     */
    @OneToMany(
            mappedBy = "breedingLineId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference("dino-line")
    private List<Dinosaur> dinosaurs;

    /**
     * Creature species associated with this breeding line (e.g., Rex, Therizino).
     */
    @ManyToOne
    @JoinColumn(name = "species_id")
    @JsonBackReference("creature-line")
    private Creature creature;

    /**
     * Server configuration applied to computations for this breeding line.
     */
    @ManyToOne
    private Servers server;

    /**
     * Soft delete flag. True indicates this line is considered deleted/inactive.
     */
    @Column(name = "deleted_flag")
    private boolean deleted = false;

    @Override
    public String toString() {
        return "Breeding Line: " + this.breedingLineId + " Line Name: " + this.lineName;
    }
}
