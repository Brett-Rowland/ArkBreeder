package org.example.backend.ValueObjects;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Stats {

    public enum STATS{
        HEALTH, // 0
        STAMINA, // 1
        CHARGE_CAPACITY, // 2

        OXYGEN, // 3
        CHARGE_REGENERATION, // 4

        FOOD, // 5
        WEIGHT, // 6

        MELEE , // 7
        CHARGE_EMISSION_RANGE, // 8

        CRAFTING, // 9
    }
    @Enumerated(value = EnumType.ORDINAL)
    public STATS statType;

}
