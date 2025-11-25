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
        HEALTH, STAMINA, OXYGEN, FOOD, WEIGHT, MELEE, CRAFTING
    }


    @Enumerated(value = EnumType.ORDINAL)
    public STATS statType;

}
