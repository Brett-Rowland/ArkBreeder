package org.example.backend.Repo;

import org.example.backend.Domains.DinoColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DinoColorsRepo.
 *
 * Repository interface for managing {@link DinoColors} entities.
 *
 * This repository provides basic CRUD operations for the
 * dinosaur-to-color mapping table, which connects individual
 * dinosaurs to their assigned Ark colors per color region.
 *
 * Notes:
 * - {@link DinoColors} acts as a join entity between Dinosaur
 *   and ArkColors.
 * - No custom queries are currently required, as standard
 *   CRUD operations are sufficient.
 * - Additional queries can be added in the future if
 *   region-based or color-based lookups are needed.
 */
@Repository
public interface DinoColorsRepo extends JpaRepository<DinoColors, Integer> {
}
