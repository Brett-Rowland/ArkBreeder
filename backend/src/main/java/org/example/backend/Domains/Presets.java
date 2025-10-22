package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "presets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Presets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preset_id")
    private Long presetID;

    @Column(name = "title")
    private String presetName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Users user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id") // No need for referencedColumnName
    private Settings settings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "breeding_line_id")
    private BreedingLine breedingLine;
}
