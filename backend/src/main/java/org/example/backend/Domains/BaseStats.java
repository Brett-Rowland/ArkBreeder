package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.backend.ValueObjects.StatsDefaults;

@Table(name = "base_stats")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseStats {
    public enum STATS{
        HEALTH, STAMINA, FOOD, WEIGHT, OXYGEN, MELEE, CRAFTING
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private STATS statType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatureId")
    @JsonBackReference("creature-base")
    @ToString.Exclude
    private Creature creature;

    @Embedded
    private StatsDefaults stats;

}
