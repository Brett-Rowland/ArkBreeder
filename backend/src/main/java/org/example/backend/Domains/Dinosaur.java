package org.example.backend.Domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.ValueObjects.Stats;

/* This class holds basic dinosaur characteristics from each breeding lines. So this holds each personal dino
    Does not hold any of the base stats or other things like that.
*/
@Data
@Table (name = "dinosaur")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dinosaur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dino_id;

    @Column (nullable = true, length = 127)
    private String dinosaur_nickname;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "statPoints", column = @Column(name = "health_points")),
            @AttributeOverride(name = "mutationCount", column = @Column(name = "health_mutations"))
    })
    private Stats health;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "statPoints", column = @Column(name = "stamina_points")),
            @AttributeOverride(name = "mutationCount", column = @Column(name = "stamina_mutations"))
    })
    private Stats stamina;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "statPoints", column = @Column(name = "food_points")),
            @AttributeOverride(name = "mutationCount", column = @Column(name = "food_mutations"))
    })
    private Stats food;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "statPoints", column = @Column(name = "oxygen_points")),
            @AttributeOverride(name = "mutationCount", column = @Column(name = "oxygen_mutations"))
    })
    private Stats oxygen;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "statPoints", column = @Column(name = "weight_points")),
            @AttributeOverride(name = "mutationCount", column = @Column(name = "weight_mutations"))
    })
    private Stats weight;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "statPoints", column = @Column(name = "melee_points")),
            @AttributeOverride(name = "mutationCount", column = @Column(name = "melee_mutations"))
    })
    private Stats melee;
}
