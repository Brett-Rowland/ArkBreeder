package org.example.backend.Repo;

import org.example.backend.Domains.ArkColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * ArkColorsRepo.
 *
 * Repository interface for managing {@link ArkColors} entities.
 *
 * This repository provides data access operations for Ark color
 * definitions, which act as static/reference data used when resolving
 * dinosaur color assignments.
 *
 * Extends {@link JpaRepository} to provide standard CRUD operations
 * and defines additional query methods specific to Ark color lookups.
 *
 * Notes:
 * - Ark colors are treated as reference data and are not frequently modified.
 * - Queries in this repository are primarily used to resolve color IDs
 *   to display metadata (name and hex code).
 */
@Repository
public interface ArkColorsRepo extends JpaRepository<ArkColors, Integer> {

    /**
     * Retrieves a single Ark color by its in-game color ID.
     *
     * @param id in-game Ark color ID
     * @return matching {@link ArkColors} record
     */
    ArkColors getArkColorsByColorId(long id);

    /**
     * Deletes an Ark color by its in-game color ID.
     *
     * @param id in-game Ark color ID
     */
    void deleteArkColorsByColorId(long id);

    /**
     * Retrieves multiple Ark colors by a set of in-game color IDs.
     *
     * @param colorIds set of Ark color IDs to retrieve
     * @return list of matching {@link ArkColors} records
     */
    @Query("SELECT ac FROM ArkColors ac WHERE ac.colorId IN (?1)")
    List<ArkColors> getArkColorsByColorIds(Set<Long> colorIds);
}
