package org.example.backend.Service;

import lombok.AllArgsConstructor;

import org.example.backend.DTOs.CreatureInput;
import org.example.backend.DTOs.ValidationCreatureDTO;
import org.example.backend.DTOs.StatsDTO;
import org.example.backend.Domains.BaseStats;
import org.example.backend.Domains.ColorRegions;
import org.example.backend.Domains.Creature;
import org.example.backend.Repo.BaseStatsRepo;
import org.example.backend.Repo.ColorRegionRepo;
import org.example.backend.Repo.CreatureRepo;
import org.example.backend.ValueObjects.Stats;
import org.example.backend.ValueObjects.StatsDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CreatureService.
 *
 * Service layer responsible for managing creature/species reference data.
 *
 * Responsibilities:
 * - create creature species records (including base stats and color regions)
 * - retrieve creature species by name or list all creatures
 * - support validation workflows:
 *   - list unvalidated creatures
 *   - mark creatures as validated
 *   - retrieve stat type lists for validation setup
 *
 * Notes:
 * - Creature records represent species-level reference data (not per-dinosaur instances).
 * - Creation involves writing multiple related tables:
 *   - {@link Creature}
 *   - {@link ColorRegions}
 *   - {@link BaseStats}
 * - Validation state is tracked via the {@code validated} flag on {@link Creature}.
 */
@Service
@AllArgsConstructor
public class CreatureService {

    /** Repository for creature/species persistence and validation queries. */
    private final CreatureRepo creatureRepo;

    /** Repository for creature color region definitions. */
    private final ColorRegionRepo colorRegionRepo;

    /** Repository for creature base stat definitions. */
    private final BaseStatsRepo baseStatsRepo;

    /**
     * Internal helper for converting a {@link CreatureInput} payload into
     * a persisted {@link Creature} species record with its related tables.
     *
     * Persisted effects:
     * - creates a {@link Creature} entity
     * - creates and saves associated {@link ColorRegions} records
     * - creates and saves associated {@link BaseStats} records
     *
     * Input expectations:
     * - {@link CreatureInput#getColorRegions()} returns a 2D array where each row contains:
     *   - [0] = color region index
     *   - [1] = visibility enum ordinal (mapped to {@link ColorRegions.Visibility})
     * - {@link CreatureInput#getStats()} returns a 2D array where each row contains:
     *   - base value
     *   - increment per point
     *   - stat multiplicand
     *   - stat additive
     *   - stat type ordinal (mapped to {@link Stats.STATS})
     *
     * Notes:
     * - This method constructs the full object graph and sets the parent relationship
     *   on each child entity (creature).
     *
     * @param creature incoming creature/species payload
     */
    void inputCreature(CreatureInput creature) {
        Creature newCreature = new Creature();
        List<ColorRegions> colorRegions = new ArrayList<>();
        List<BaseStats> stats = new ArrayList<>();

        newCreature.setCreatureName(creature.getCreatureName());

        // Build color region entities
        int[][] colorRegionsTransfer = creature.getColorRegions();
        for (int i = 0; i < colorRegionsTransfer.length; i++) {
            ColorRegions cr = new ColorRegions();
            cr.setCreature(newCreature);
            cr.setColorRegion(colorRegionsTransfer[i][0]);
            cr.setRegionDescriptor(creature.getColorRegionDescriptor()[i]);
            cr.setVisibility(ColorRegions.Visibility.values()[colorRegionsTransfer[i][1]]);
            colorRegions.add(cr);
        }

        // Build base stat entities
        float[][] statsTransfer = creature.getStats();
        for (float[] floats : statsTransfer) {
            BaseStats bs = new BaseStats();
            bs.setCreature(newCreature);
            bs.setStats(new StatsDefaults(floats[0], floats[1], floats[2], floats[3]));
            bs.getStats().setStatType(Stats.STATS.values()[(int) floats[4]]);
            stats.add(bs);
        }

        // Persist creature and its related definitions
        newCreature.setColorRegions(colorRegions);
        newCreature.setBaseStats(stats);

        creatureRepo.save(newCreature);
        colorRegionRepo.saveAll(colorRegions);
        baseStatsRepo.saveAll(stats);
    }

    /**
     * Creates and persists a new creature species record.
     *
     * Persisted effects:
     * - delegates to {@link #inputCreature(CreatureInput)} to save creature,
     *   color regions, and base stats.
     *
     * @param creature creature/species input payload
     * @return status message
     */
    public String createCreature(CreatureInput creature) {
        inputCreature(creature);
        return "Created Creature Successfully";
    }

    /**
     * Retrieves a creature species by name (case-insensitive lookup handled by repository).
     *
     * @param creatureName creature/species name
     * @return matching {@link Creature}
     */
    public Creature getCreature(String creatureName) {
        return creatureRepo.getCreatureByName(creatureName);
    }

    /**
     * Retrieves all creature species records.
     *
     * @return list of {@link Creature} entities
     */
    public List<Creature> getCreatures() {
        return creatureRepo.findAll();
    }

    /**
     * Bulk-creates multiple creature species records.
     *
     * Persisted effects:
     * - each {@link CreatureInput} is converted and saved via {@link #inputCreature(CreatureInput)}.
     *
     * @param creatures list of creature/species input payloads
     * @return status message
     */
    public String createCreatureList(List<CreatureInput> creatures) {
        for (CreatureInput creature : creatures) {
            inputCreature(creature);
        }
        return "Created Creatures Successfully";
    }

    /**
     * Retrieves all creatures that have not yet been validated.
     *
     * Used for validation workflows to identify incomplete/unverified creatures.
     *
     * @return list of {@link ValidationCreatureDTO} summary records
     */
    public List<ValidationCreatureDTO> getUnvalidatedCreatures() {
        return creatureRepo.getUnvalidatedCreatures();
    }

    /**
     * Marks a creature as validated.
     *
     * Persisted effects:
     * - sets {@link Creature#validated} to true and saves the creature.
     *
     * @param creatureId creature/species identifier
     * @throws Exception if an error occurs during update
     */
    public void updateValidation(long creatureId) throws Exception {
        try {
            Creature creature = creatureRepo.getCreatureByCreatureId(creatureId);
            creature.setValidated(true);
            creatureRepo.save(creature);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Retrieves the ordered list of stat types for a creature species.
     *
     * Used primarily during validation to initialize stat rows without needing
     * the full base stat records.
     *
     * @param creatureId creature/species identifier
     * @return ordered list of {@link StatsDTO} containing only statType
     */
    public List<StatsDTO> getCreatureStatTypes(long creatureId) {
        return baseStatsRepo.getStatTypeASCByID(creatureId);
    }
}
