package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "server")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servers {


    public enum serverType {
        OFFICIAL, CUSTOM
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private Long serverId;

    @Column(name = "server_name")
    private String serverName;


    @Column(name = "server_type")
    @Enumerated(value = EnumType.ORDINAL)
    private serverType serverType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Users user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id") // No need for referencedColumnName
    private Settings settings;

    @OneToMany(mappedBy = "server",cascade = CascadeType.ALL)
    private List<BreedingLine> breedingLine;
}
