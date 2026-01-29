package org.example.backend.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.backend.Domains.Servers;
import org.example.backend.Domains.Users;
import org.example.backend.Domains.BreedingSettings;
import org.example.backend.Repo.BreedingSettingsRepo;
import org.example.backend.Repo.ServersRepo;
import org.example.backend.Repo.UsersRepo;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * ServersService.
 *
 * Service layer responsible for managing server configurations
 * and their associated settings.
 *
 * Responsibilities:
 * - retrieve server settings
 * - list servers owned by a user
 * - create new server configurations
 * - update server metadata and settings
 * - delete servers and clean up associated settings
 *
 * Notes:
 * - Servers represent a logical grouping of breeding settings.
 * - Each server owns exactly one {@link BreedingSettings} record.
 * - OFFICIAL servers are global and not tied to a specific user.
 */
@Service
@AllArgsConstructor
public class ServersService {

    /** Repository for settings persistence and retrieval. */
    BreedingSettingsRepo  breedingSettingsRepo;

    /** Repository for server persistence and lookup. */
    ServersRepo serversRepo;

    /** Repository for user lookup and ownership resolution. */
    UsersRepo usersRepo;

    /**
     * Retrieves a settings record by its unique identifier.
     *
     * @param serversId unique identifier of the server record
     * @return matching {@link Servers} entity
     */
    public Servers getSettings(Long serversId) {
        return serversRepo.getServersByServerId(serversId);
    }

    /**
     * Retrieves all servers owned by a user.
     *
     * This method resolves the user via authentication token and
     * returns all associated server configurations.
     *
     * @param token authentication token identifying the user
     * @return list of {@link Servers} owned by the user
     */
    public List<Servers> getServers(Long token) {
        Users user = usersRepo.getUsersByToken(token);
        return serversRepo.getServersByUser(user);
    }

    /**
     * Deletes a server and its associated settings.
     *
     * Persisted effects:
     * - deletes the {@link BreedingSettings} records tied to the server
     * - deletes the {@link Servers} record itself
     *
     * Notes:
     * - Settings are deleted explicitly to ensure cleanup
     *   before removing the server.
     *
     * @param serverID unique identifier of the server to delete
     */
    @Transactional
    public void deleteServers(Long serverID) {
        Servers server = serversRepo.getServersByServerId(serverID);
        List<BreedingSettings> breedingSettings = server.getBreedingSettings();
        breedingSettingsRepo.deleteAll(breedingSettings);
        serversRepo.delete(server);
    }

    /**
     * Updates an existing server configuration.
     *
     * Persisted effects:
     * - replaces the server's {@link BreedingSettings} reference
     * - updates the server display name
     * - deletes the previous settings record to prevent orphaned data
     *
     * Notes:
     * - This method assumes the incoming {@link Servers} object
     *   represents a full replacement configuration.
     *
     * @param serverID unique identifier of the server to update
     * @param newServer new server configuration to apply
     * @param serverName new display name for the server
     */
    @Transactional
    public void updateServer(Long serverID, Servers newServer, String serverName) {
        Servers server = serversRepo.getServersByServerId(serverID);

        List<BreedingSettings> newBreedingSettings = newServer.getBreedingSettings();
        List<BreedingSettings> oldBreedingSettings = new ArrayList<>(server.getBreedingSettings());

        server.setServerName(serverName);
        server.transferSettingsToServer(newServer);

        for  (BreedingSettings newBreedingSetting : newBreedingSettings) {
            newBreedingSetting.setServer(server);
        }

        server.setBreedingSettings(newBreedingSettings);

        serversRepo.save(server);
        breedingSettingsRepo.saveAll(newBreedingSettings);

        breedingSettingsRepo.deleteAll(oldBreedingSettings);
        // Clean up the previous settings record
    }

    /**
     * Creates a new server configuration.
     *
     * Persisted effects:
     * - saves the associated {@link BreedingSettings} record
     * - saves the {@link Servers} record
     *
     * Ownership rules:
     * - OFFICIAL servers are not tied to a user
     * - CUSTOM servers are assigned to the authenticated user
     *
     * @param server server configuration to create
     * @param token authentication token identifying the user
     */
    public void createServer(Servers server, Long token) {
        if (server.getServerType() != Servers.serverType.OFFICIAL) {
            server.setUser(usersRepo.getUsersByToken(token));
        }
        for (BreedingSettings newBreedingSetting : server.getBreedingSettings()) {
            newBreedingSetting.setServer(server);
        }
        serversRepo.save(server);
        breedingSettingsRepo.saveAll(server.getBreedingSettings());

    }
}
