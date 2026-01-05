package org.example.backend.Repo;

import org.example.backend.Domains.Dinosaur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DinosaurRepo.
 *
 * Repository interface for managing {@link Dinosaur} entities.
 *
 * This repository provides data access operations for individual
 * dinosaurs belonging to breeding lines. Dinosaurs represent
 * per-instance creatures rather than species-level definitions.
 *
 * Notes:
 * - Dinosaurs are typically accessed by ID during editing,
 *   deletion, and computation workflows.
 * - Standard {@link JpaRepository} operations are sufficient
 *   for current use cases.
 */
@Repository
public interface DinosaurRepo extends JpaRepository<Dinosaur, Long> {

    /**
     * Retrieves a dinosaur by its unique identifier.
     *
     * @param DinosaurId unique identifier of the dinosaur
     * @return matching {@link Dinosaur} entity
     */
    Dinosaur getDinosaurByDinoId(Long DinosaurId);
}
