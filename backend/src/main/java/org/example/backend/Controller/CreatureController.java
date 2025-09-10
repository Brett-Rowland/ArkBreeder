package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.Creature;
import org.example.backend.Service.CreatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/creatures")
public class CreatureController {

    private final CreatureService creatureService;


    @PostMapping("/new-species")
    public ResponseEntity<?> newCreature(@RequestBody Creature species) {
        return new ResponseEntity<>(creatureService.createCreature(species), HttpStatus.OK);
    }


    @GetMapping("/current-species")
    public ResponseEntity<?> getCurrentSpecies(@RequestBody String creatureName) {
        System.out.println(creatureName);
        return new ResponseEntity<>(creatureService.getCreature(creatureName), HttpStatus.OK);
    }

}
