package org.example.backend.Repo;

import org.example.backend.Domains.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UsersRepo.
 *
 * Repository interface for managing {@link Users} entities.
 *
 * This repository provides data access operations related to
 * user accounts, authentication, and account existence checks.
 *
 * Notes:
 * - Users are identified internally by both user ID and token.
 * - Authentication and uniqueness checks are handled at the
 *   repository level for efficiency.
 */
@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    /**
     * Checks if a username already exists.
     *
     * @param username username to check
     * @return true if the username exists, false otherwise
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a token already exists.
     *
     * @param token authentication token
     * @return true if the token exists, false otherwise
     */
    Boolean existsByToken(Long token);

    /**
     * Retrieves a user by authentication token.
     *
     * @param token authentication token
     * @return matching {@link Users} entity
     */
    Users findByToken(Long token);

    /**
     * Retrieves a user by username and password.
     *
     * Used during the login workflow.
     *
     * @param username account username
     * @param password account password
     * @return optional containing the matching {@link Users}, if found
     */
    Optional<Users> findByUsernameAndPassword(String username, String password);

    /**
     * Retrieves a user by authentication token.
     *
     * Alternative naming for token-based lookup.
     *
     * @param token authentication token
     * @return matching {@link Users} entity
     */
    Users getUsersByToken(Long token);
}
