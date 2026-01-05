package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.Servers;
import org.example.backend.Domains.Settings;
import org.example.backend.Service.ServersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Servers and their associated Settings.
 *
 * A Server represents a configuration context used for stat computations
 * (e.g., taming multipliers, breeding multipliers, stat multipliers, imprinting).
 *
 * Settings are currently modeled as a component of a Server and define
 * the calculation behavior applied across breeding lines and dinosaurs.
 *
 * Responsibilities:
 * - Create servers for a user
 * - Retrieve servers owned by a user
 * - Retrieve settings tied to a server
 * - Update server settings and server name
 * - Soft-delete servers
 *
 * All business logic is delegated to {@link ServersService}.
 * All endpoints assume the user is authenticated.
 */
@RestController
@RequestMapping("/server/settings")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ServersController {

    private ServersService serversService;

    /**
     * Retrieves the settings associated with a specific server.
     *
     * Path variables:
     * - settingsId: ID of the settings object associated with a server
     *
     * Returns:
     * - 200 OK with the server settings payload
     *
     * Notes:
     * - Settings are currently retrieved independently but are logically tied
     *   to a server entity.
     */
    @GetMapping("/{settingsId}/settings")
    public ResponseEntity<?> getSettings(@PathVariable Long settingsId) {
        return new ResponseEntity<>(serversService.getSettings(settingsId), HttpStatus.OK);
    }

    /**
     * Soft-deletes a server by ID.
     *
     * Path variables:
     * - serverID: ID of the server to delete
     *
     * Returns:
     * - 200 OK if deletion succeeds
     *
     * Notes:
     * - Deletion behavior is non-destructive (flag-based), consistent with
     *   other domain deletions (e.g., breeding lines, dinosaurs).
     */
    @DeleteMapping("/{serverID}/delete")
    public ResponseEntity<?> deleteServer(@PathVariable Long serverID) {
        serversService.deleteSettings(serverID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Updates a server's settings and display name.
     *
     * Path variables:
     * - serverId: ID of the server to update
     *
     * Request body:
     * - settings: {@link Settings} object containing updated calculation values
     *
     * Query parameters:
     * - serverName: new display name for the server
     *
     * Returns:
     * - 200 OK if the update succeeds
     *
     * Notes:
     * - This endpoint updates both the server metadata (name) and its
     *   calculation settings in a single operation.
     */
    @PutMapping("/{serverId}/update")
    public ResponseEntity<?> updateServer(
            @PathVariable Long serverId,
            @RequestBody Settings settings,
            @RequestParam String serverName) {

        serversService.updateServer(serverId, settings, serverName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves all servers owned by a specific user.
     *
     * Path variables:
     * - token: authentication token identifying the user
     *
     * Returns:
     * - 200 OK with a list of servers owned by the user
     */
    @GetMapping("/{token}")
    public ResponseEntity<?> getServers(@PathVariable Long token) {
        return new ResponseEntity<>(serversService.getServers(token), HttpStatus.OK);
    }

    /**
     * Creates a new server for a user.
     *
     * Path variables:
     * - token: authentication token identifying the user
     *
     * Request body:
     * - server: {@link Servers} object containing initial server metadata
     *   and default settings
     *
     * Returns:
     * - 200 OK if server creation succeeds
     */
    @PostMapping("/{token}/create")
    public ResponseEntity<?> createServer(@RequestBody Servers server, @PathVariable Long token) {
        serversService.createServer(server, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
