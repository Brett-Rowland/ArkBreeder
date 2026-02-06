package org.example.backend.ValueObjects;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stats.
 *
 * Base mapped superclass representing a stat type within the Ark stat system.
 *
 * This class defines the identity of a stat (e.g. Health, Melee, Weight)
 * without storing any stat values or calculations.
 *
 * Design intent:
 * - Acts as a shared base for stat-related value objects
 * - Extended by {@link StatPoints} and {@link StatsDefaults}
 * - Provides a consistent stat identity across creatures and dinosaurs
 *
 * Persistence notes:
 * - Marked as {@link MappedSuperclass} so fields are inherited
 *   by embeddable subclasses without creating a separate table.
 * - Stat type is stored as an ordinal to preserve a fixed stat order.
 *
 * Ordering:
 * - Enum order is intentional and relied upon in calculations,
 *   validation, and UI rendering.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Stats {

    /**
     * Enumeration of all supported stat types.
     *
     * IMPORTANT:
     * - Enum order must remain stable.
     * - Reordering values will break persisted data and calculations.
     */
    public enum STATS {
        HEALTH,                 // 0
        STAMINA,                // 1
        CHARGE_CAPACITY,        // 2

        OXYGEN,                 // 3
        CHARGE_REGENERATION,    // 4

        FOOD,                   // 5
        WEIGHT,                 // 6

        MELEE,                  // 7
        CHARGE_EMISSION_RANGE,  // 8

        CRAFTING                // 9
    }

    /**
     * Stat type identifier.
     *
     * Stored as an ordinal to ensure consistent ordering and compact storage.
     */
    @Enumerated(value = EnumType.ORDINAL)
    public STATS statType;



    public Boolean isLightPet(){
        return this.statType == Stats.STATS.CHARGE_CAPACITY || this.statType == Stats.STATS.CHARGE_REGENERATION || this.statType == Stats.STATS.CHARGE_EMISSION_RANGE;
    }

    public Stats.STATS getLightPetStats(){
        return switch (this.statType) {
            case CHARGE_CAPACITY -> Stats.STATS.STAMINA;
            case CHARGE_REGENERATION -> Stats.STATS.OXYGEN;
            case CHARGE_EMISSION_RANGE -> Stats.STATS.MELEE;
            default -> statType;
        };
    }
}
