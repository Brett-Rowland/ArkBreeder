package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(length = 31, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private Long token;

    @CreationTimestamp
    @Column(updatable = false, name = "created")
    private LocalDateTime createdAt;

//    Join to the Breeding Lines
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<BreedingLine> breedingLines;


    @OneToMany(mappedBy="user")
    @JsonManagedReference
    private List<Presets> presets;



}
