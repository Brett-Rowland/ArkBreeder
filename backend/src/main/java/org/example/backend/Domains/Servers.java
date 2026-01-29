package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Servers table.
 *
 * Represents a server configuration context used for all stat computations.
 *
 * A Server defines the environment in which breeding lines and dinosaurs
 * are evaluated, including calculation settings such as multipliers and
 * difficulty rules (via the associated {@link BreedingSettings} entity).
 *
 * Servers are user-owned and may represent either official Ark servers
 * or custom/private server configurations.
 *
 * Primary key:
 * - serverId: generated identifier for the server
 *
 * Relationships:
 * - user (Many-to-One): owner of the server configuration ({@link Users})
 * - settings (Many-to-One): calculation settings applied by this server ({@link BreedingSettings})
 * - breedingLine (One-to-Many): breeding lines evaluated under this server ({@link BreedingLine})
 *
 * Key fields:
 * - serverName: display name of the server
 * - serverType: type of server (OFFICIAL or CUSTOM)
 *
 * Constraints / rules:
 * - Each server has a list of Breeding Settings {@link BreedingSettings} record
 * - Servers are owned by a single {@link Users}
 *
 * Notes:
 * - Server settings directly influence all derived stat calculations
 *   performed for {@link Dinosaur} instances.
 * - This entity acts as the configuration root for stat computation.
 */
@Entity
@Table(name = "servers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servers {

    /**
     * Supported server types.
     *
     * OFFICIAL: Ark official server settings
     * CUSTOM: user-defined server settings
     */
    public enum serverType {
        OFFICIAL,
        CUSTOM
    }

    /**
     * Primary key for the server.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private Long serverId;

    /**
     * Display name of the server.
     */
    @Column(name = "server_name")
    private String serverName;

    /**
     * Classification of the server.
     */
    @Column(name = "server_type")
    @Enumerated(value = EnumType.ORDINAL)
    private serverType serverType;

    /**
     * User who owns this server configuration.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Users user;


    /**
     * Multiplier controlling creature maturation speed.
     *
     * Higher values result in faster maturation.
     */
    @Column(name = "maturing_rate")
    private Float maturingRate;

    /**
     * Multiplier controlling egg incubation speed.
     *
     * Higher values result in faster egg hatching.
     */
    @Column(name = "egg_hatch_rate")
    private Float eggHatchRate;


    /**
     * Multiplier controlling imprint effect
     *
     * Higher Values result in higher stat increase
     */
    @Column(name = "imprint_scale")
    private Float imprintScale;


    /**
     * Flag indicating whether single-player scaling rules are enabled.
     */
    @Column(name = "single_player")
    private Boolean singlePlayer;

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
    @JsonManagedReference
    @OrderBy("stats ASC")
    private List<BreedingSettings> breedingSettings;



    /**
     * Breeding lines that are evaluated under this server configuration.
     */
    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL)
    private List<BreedingLine> breedingLine;



    public void transferSettingsToServer(Servers newServers) {
        this.maturingRate = newServers.getMaturingRate();
        this.eggHatchRate = newServers.getEggHatchRate();
        this.imprintScale = newServers.getImprintScale();
        this.singlePlayer = newServers.getSinglePlayer();
    }
}
