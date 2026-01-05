package org.example.backend.Domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Users table.
 *
 * Represents a user account within the application.
 *
 * A User is the top-level owner of all user-created data, including
 * breeding lines and server configurations. Authentication and
 * authorization are handled using a token-based approach.
 *
 * Primary key:
 * - userId: generated identifier for the user account
 *
 * Relationships:
 * - breedingLines (One-to-Many): breeding lines owned by the user ({@link BreedingLine})
 * - servers (One-to-Many): server configurations owned by the user ({@link Servers})
 *
 * Key fields:
 * - username: unique display/login name for the user
 * - password: stored authentication credential
 * - token: authentication token used for identifying the user
 * - createdAt: timestamp when the account was created
 *
 * Constraints / rules:
 * - username must be unique
 * - password must be provided
 * - token is generated and managed by the authentication workflow
 *
 * Notes:
 * - Users act as the root ownership entity for all domain data.
 * - Deleting a user implicitly removes or deactivates all owned data.
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {

    /**
     * Primary key for the user account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    /**
     * Unique username for the user.
     *
     * Used for login and display purposes.
     */
    @Column(length = 31, nullable = false)
    private String username;

    /**
     * Password credential for authentication.
     *
     * Stored in a hashed/encrypted form.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Authentication token associated with the user.
     *
     * Used to identify the user in authenticated API requests.
     */
    @Column(nullable = true)
    private Long token;

    /**
     * Timestamp indicating when the user account was created.
     *
     * Automatically generated and not updatable.
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created")
    private LocalDateTime createdAt;

    /**
     * Breeding lines owned by this user.
     */
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<BreedingLine> breedingLines;

    /**
     * Server configurations owned by this user.
     */
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Servers> servers;
}
