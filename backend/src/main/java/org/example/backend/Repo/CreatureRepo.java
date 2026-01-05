package org.example.backend.Repo;

import org.example.backend.DTOs.CreatureDTO;
import org.example.backend.Domains.Creature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CreatureRepo.
 *
 * Repository interface for managing {@link Creature} entities.
 *
 * This repository provides data access methods for creature species
 * definitions, including lookup by name, validation workflows, and
 * lightweight listings for UI consumption.
 *
 * Notes:
 * - Creature records represent species-level reference data.
 * - Several queries return {@link CreatureDTO} projections to avoid
 *   loading full entity graphs when only summary data is required.
 */
@Repository
public interface CreatureRepo extends JpaRepository<Creature, Long> {

    /**
     * Retrieves a creature species by name (case-insensitive).
     *
     * @param creatureName display name of the creature species
     * @return matching {@link Creature} entity, or null if not found
     */
    @Query("SELECT cs FROM Creature cs WHERE UPPER(cs.creatureName) = UPPER(?1)")
    Creature getCreatureByName(String creatureName);

    /**
     * Retrieves a creature species by its unique identifier.
     *
     * @param creatureId unique identifier of the creature species
     * @return matching {@link Creature} entity
     */
    Creature getCreatureByCreatureId(long creatureId);

    /**
     * Retrieves all unvalidated creature species.
     *
     * This query is used during the validation workflow and returns
     * a lightweight DTO containing:
     * - creature ID
     * - creature name
     * - total number of defined color regions
     *
     * Results are ordered alphabetically by creature name.
     *
     * @return list of {@link CreatureDTO} for unvalidated creatures
     */
    @Query(
            "SELECT new org.example.backend.DTOs.CreatureDTO(" +
                    "  cs.creatureId, cs.creatureName, COUNT(cr)" +
                    ") " +
                    "FROM Creature cs " +
                    "JOIN ColorRegions cr ON cr.creature.creatureId = cs.creatureId " +
                    "WHERE cs.validated = false " +
                    "GROUP BY cs.creatureId, cs.creatureName " +
                    "ORDER BY cs.creatureName"
    )
    List<CreatureDTO> getUnvalidatedCreatures();

    /**
     * Retrieves all validated creature species.
     *
     * This query returns a lightweight DTO containing only the
     * creature ID and name, suitable for dropdowns and selection lists.
     *
     * Results are ordered alphabetically by creature name.
     *
     * @return list of {@link CreatureDTO} for validated creatures
     */
    @Query(
            "SELECT new org.example.backend.DTOs.CreatureDTO(cs.creatureId, cs.creatureName) " +
                    "FROM Creature cs " +
                    "WHERE cs.validated = true " +
                    "ORDER BY cs.creatureName"
    )
    List<CreatureDTO> getValidatedCreatures();
}
