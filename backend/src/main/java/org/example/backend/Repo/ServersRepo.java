package org.example.backend.Repo;

import org.example.backend.DTOs.SettingsDTO;
import org.example.backend.Domains.Servers;
import org.example.backend.Domains.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ServersRepo.
 *
 * Repository interface for managing {@link Servers} entities.
 *
 * This repository provides access to server configurations associated
 * with users, including both user-created servers and globally available
 * official servers.
 *
 * Notes:
 * - Servers represent a combination of server metadata and attached settings.
 * - Several queries return {@link SettingsDTO} projections to avoid loading
 *   full server and settings graphs when only summary data is required.
 */
@Repository
public interface ServersRepo extends JpaRepository<Servers, Long> {

    /**
     * Retrieves all servers owned by a specific user.
     *
     * @param user user entity
     * @return list of {@link Servers} owned by the user
     */
    List<Servers> getServersByUser(Users user);

    /**
     * Retrieves a server by its unique identifier.
     *
     * @param serverId unique identifier of the server
     * @return matching {@link Servers} entity
     */
    Servers getServersByServerId(Long serverId);

    /**
     * Retrieves all servers available to a user based on authentication token.
     *
     * This query returns:
     * - all servers owned by the authenticated user
     * - all official servers (serverType = OFFICIAL)
     *
     * Results are returned as {@link SettingsDTO} projections containing:
     * - server ID
     * - server name
     *
     * Ordering:
     * - official servers first
     * - followed by user-created servers
     * - alphabetically by server name
     *
     * @param token authentication token identifying the user
     * @return list of {@link SettingsDTO} representing available servers
     */
    @Query(
            "SELECT new org.example.backend.DTOs.SettingsDTO(sr.serverId, sr.serverName) " +
                    "FROM Servers sr " +
                    "LEFT JOIN Users us ON sr.user.userId = us.userId " +
                    "WHERE us.token = :token OR sr.serverType = 0 " +
                    "ORDER BY sr.serverType, sr.serverName"
    )
    List<SettingsDTO> getServersByToken(@Param("token") Long token);


//    @Query("SELECT sr from Servers sr WHERE sr.serverName = :name and sr.serverType = Servers.serverType.OFFICIAL")
//    Servers getOfficialServerStats(@Param("name"));
}
