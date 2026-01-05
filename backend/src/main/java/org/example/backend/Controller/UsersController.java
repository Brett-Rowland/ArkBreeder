package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.Users;
import org.example.backend.Service.BreedingLinesService;
import org.example.backend.Service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Users (accounts and authentication).
 *
 * A User represents an application account used to access and manage
 * breeding lines, dinosaurs, creatures, and server settings.
 *
 * Responsibilities:
 * - Create user accounts
 * - Authenticate users (login)
 * - Delete user accounts
 *
 * All business logic is delegated to {@link UsersService}.
 * All endpoints assume the user is authenticated unless explicitly related to
 * account creation or login.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    private final UsersService usersService;
    private final BreedingLinesService breedingLinesService;

    /**
     * Creates a new user account.
     *
     * Request body:
     * - user: {@link Users} object containing account creation fields
     *   (e.g., username and password as applicable)
     *
     * Returns:
     * - 200 OK with the created user payload (or creation result)
     *
     * Error cases:
     * - 226 IM USED if the username is already taken
     * - 500 INTERNAL SERVER ERROR if an unexpected error occurs
     *
     * Notes:
     * - This endpoint is used for registration/account creation.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Users user) {
        try {
            return new ResponseEntity<>(usersService.create(user), HttpStatus.OK);
        } catch (RuntimeException e) {
            // Error if the username is taken
            return new ResponseEntity<>(HttpStatus.IM_USED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a user account by token.
     *
     * Path variables:
     * - token: authentication token identifying the user account to delete
     *
     * Returns:
     * - 200 OK if deletion succeeds
     *
     * Error cases:
     * - 500 INTERNAL SERVER ERROR if deletion fails unexpectedly
     *
     * Notes:
     * - This endpoint deletes the account associated with the provided token.
     */
    @DeleteMapping("/{token}/delete")
    public ResponseEntity<?> deleteAccount(@PathVariable Long token) {
        try {
            usersService.delete(token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Authenticates a user and performs login.
     *
     * Request body:
     * - user: {@link Users} object containing login credentials
     *
     * Returns:
     * - 200 OK with the login result (typically includes an authentication token)
     *
     * Error cases:
     * - 500 INTERNAL SERVER ERROR if an unexpected error occurs
     *
     * Notes:
     * - If invalid credentials are possible, consider returning 401 UNAUTHORIZED
     *   or 400 BAD REQUEST depending on your preferred API behavior.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        try {
            return new ResponseEntity<>(usersService.login(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
