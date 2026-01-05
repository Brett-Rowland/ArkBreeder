package org.example.backend.Service;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.Servers;
import org.example.backend.Domains.Settings;
import org.example.backend.Domains.Users;
import org.example.backend.Repo.ServersRepo;
import org.example.backend.Repo.SettingsRepo;
import org.example.backend.Repo.UsersRepo;
import org.springframework.stereotype.Service;

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
 * - Each server owns exactly one {@link Settings} record.
 * - OFFICIAL servers are global and not tied to a specific user.
 */
@Service
@AllArgsConstructor
public class ServersService {

    /** Repository for settings persistence and retrieval. */
    SettingsRepo settingsRepo;

    /** Repository for server persistence and lookup. */
    ServersRepo serversRepo;

    /** Repository for user lookup and ownership resolution. */
    UsersRepo usersRepo;

    /**
     * Retrieves a settings record by its unique identifier.
     *
     * @param settingsId unique identifier of the settings record
     * @return matching {@link Settings} entity
     */
    public Settings getSettings(Long settingsId) {
        return settingsRepo.getSettingsBySettingsId(settingsId);
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
     * - deletes the {@link Settings} record tied to the server
     * - deletes the {@link Servers} record itself
     *
     * Notes:
     * - Settings are deleted explicitly to ensure cleanup
     *   before removing the server.
     *
     * @param serverID unique identifier of the server to delete
     */
    public void deleteSettings(Long serverID) {
        Servers server = serversRepo.getServersByServerId(serverID);
        Long settingsId = server.getSettings().getSettingsId();

        Settings settings = settingsRepo.getSettingsBySettingsId(settingsId);

        settingsRepo.delete(settings);
        serversRepo.delete(server);
    }

    /**
     * Updates an existing server configuration.
     *
     * Persisted effects:
     * - replaces the server's {@link Settings} reference
     * - updates the server display name
     * - deletes the previous settings record to prevent orphaned data
     *
     * Notes:
     * - This method assumes the incoming {@link Settings} object
     *   represents a full replacement configuration.
     *
     * @param serverID unique identifier of the server to update
     * @param settings new settings configuration to apply
     * @param serverName new display name for the server
     */
    public void updateServer(Long serverID, Settings settings, String serverName) {
        Servers server = serversRepo.getServersByServerId(serverID);
        Long oldSettingsId = server.getSettings().getSettingsId();

        server.setSettings(settings);
        server.setServerName(serverName);
        serversRepo.save(server);

        // Clean up the previous settings record
        settingsRepo.deleteBySettingsId(oldSettingsId);
    }

    /**
     * Creates a new server configuration.
     *
     * Persisted effects:
     * - saves the associated {@link Settings} record
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

        settingsRepo.save(server.getSettings());
        serversRepo.save(server);
    }
}
