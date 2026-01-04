package org.example.backend.Repo;



import org.example.backend.DTOs.SettingsTransfer;
import org.example.backend.Domains.Servers;
import org.example.backend.Domains.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServersRepo extends JpaRepository<Servers, Long> {


    List<Servers> getServersByUser(Users user);

    Servers getServersByServerId(Long serverId);

    @Query("SELECT new org.example.backend.DTOs.SettingsTransfer(sr.serverId, sr.serverName) from Servers sr LEFT JOIN Users us on sr.user.userId = us.userId WHERE us.token = :token or sr.serverType = 0 ORDER BY sr.serverType, sr.serverName")
    List<SettingsTransfer> getServersByToken(@Param("token") Long token);
}
