package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.Servers;
import org.example.backend.Domains.Settings;
import org.example.backend.Service.ServersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/settings")
@AllArgsConstructor
public class ServersController {

    private ServersService serversService;


//    get settings
    @GetMapping("/{settingsId}/settings")
    public ResponseEntity<?> getSettings(@PathVariable Long settingsId) {
        return new ResponseEntity<>(serversService.getSettings(settingsId), HttpStatus.OK);
    }

//    delete settings
    @DeleteMapping("/{serverID}/delete")
    public ResponseEntity<?> deleteServer(@PathVariable Long serverID) {
        serversService.deleteSettings(serverID);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    update settings
//    Need to add a way to rename it within this
    @PutMapping("/{serverId}/update")
    public ResponseEntity<?> updateServer(@PathVariable Long serverId, @RequestBody Settings settings, @RequestParam String serverName) {
        serversService.updateServer(serverId, settings, serverName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getServers(@PathVariable Long token) {
        return new ResponseEntity<>(serversService.getServers(token), HttpStatus.OK);
    }


    @PostMapping("/{token}/create")
    ResponseEntity<?> createServer(@RequestBody Servers server, @PathVariable Long token) {
        serversService.createServer(server, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
