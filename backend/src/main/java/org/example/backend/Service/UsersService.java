package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.Users;
import org.example.backend.Repo.UsersRepo;
import org.springframework.stereotype.Service;

/**
 * UsersService.
 *
 * Service layer responsible for managing user accounts and authentication.
 *
 * Responsibilities:
 * - create user accounts
 * - authenticate users via username/password
 * - delete user accounts
 * - generate and manage authentication tokens
 *
 * Notes:
 * - Authentication is token-based rather than session-based.
 * - Tokens are randomly generated numeric values used to identify users
 *   across API requests.
 * - Passwords are stored and validated directly (no hashing shown here).
 */
@AllArgsConstructor
@Service
public class UsersService {

    /** Repository for user persistence and authentication queries. */
    private final UsersRepo usersRepo;

    /**
     * Creates a new user account.
     *
     * Workflow:
     * - validates that the username is not already taken
     * - generates a unique authentication token
     * - saves the user record
     *
     * Persisted effects:
     * - writes a new {@link Users} record to the database
     *
     * Notes:
     * - The generated token is returned to allow immediate login
     *   after successful account creation.
     *
     * @param user user account payload
     * @return generated authentication token
     * @throws RuntimeException if the username already exists
     */
    public Long create(Users user) {

        // Ensure username uniqueness
        if (usersRepo.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Generate a unique authentication token
        // Token avoids exposing sensitive data like username/password
        user.setToken(setToken());

        // Persist the user
        usersRepo.save(user);

        // Return token for immediate authentication
        return user.getToken();
    }

    /**
     * Deletes a user account based on authentication token.
     *
     * Persisted effects:
     * - removes the {@link Users} record from the database
     *
     * Notes:
     * - This is a hard delete.
     * - Related data (breeding lines, servers, etc.) should be
     *   handled via cascade rules or service-level cleanup if needed.
     *
     * @param token authentication token identifying the user
     */
    public void delete(Long token) {

        // Resolve user by token
        Users user = usersRepo.findByToken(token);

        // Delete the user record
        usersRepo.delete(user);
    }

    /**
     * Authenticates a user using username and password.
     *
     * Workflow:
     * - attempts to find a matching user record
     * - returns the user's authentication token if successful
     *
     * Notes:
     * - Authentication failures throw a runtime exception.
     * - No token regeneration occurs on login; existing token is reused.
     *
     * @param user login payload containing username and password
     * @return authentication token
     * @throws RuntimeException if credentials are invalid
     */
    public long login(Users user) {

        Users login = usersRepo
                .findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .orElse(null);

        if (login == null) {
            throw new RuntimeException("Invalid username or password");
        }

        return login.getToken();
    }

    /**
     * Generates a unique authentication token.
     *
     * Token generation rules:
     * - random numeric value
     * - retried until a unique token is found
     *
     * Notes:
     * - Token uniqueness is enforced via repository existence checks.
     * - Token range is limited but collision-resistant for current scale.
     *
     * @return unique authentication token
     */
    private long setToken() {
        long token;
        do {
            token = (long) Math.ceil(Math.random() * 10000000);
        } while (usersRepo.existsByToken(token)); // retry if it already exists
        return token;
    }
}
