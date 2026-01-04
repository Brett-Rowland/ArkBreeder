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


@Service
@AllArgsConstructor
public class ServersService {

    SettingsRepo settingsRepo;
    ServersRepo serversRepo;
    UsersRepo usersRepo;


    public Settings getSettings(Long settingsId) {
        return settingsRepo.getSettingsBySettingsId(settingsId);
    }

    public List<Servers> getServers(Long token) {
        Users user = usersRepo.getUsersByToken(token);
        return serversRepo.getServersByUser(user);

    }

    public void deleteSettings(Long serverID) {
        Servers server = serversRepo.getServersByServerId(serverID);
        Long settingsId =  server.getSettings().getSettingsId();
        Settings settings = settingsRepo.getSettingsBySettingsId(settingsId);
        settingsRepo.delete(settings);
        serversRepo.delete(server);

    }

    public void updateServer(Long serverID, Settings settings, String serverName) {
        Servers server = serversRepo.getServersByServerId(serverID);
        Long oldSettingsId = server.getSettings().getSettingsId();

        server.setSettings(settings);
        server.setServerName(serverName);
        serversRepo.save(server);
        settingsRepo.deleteBySettingsId(oldSettingsId); // clean up old one
    }


    public void createServer(Servers server, Long token){
        if (server.getServerType() != Servers.serverType.OFFICIAL){
            server.setUser(usersRepo.getUsersByToken(token));
        }
        settingsRepo.save(server.getSettings());
        serversRepo.save(server);
    }
}
