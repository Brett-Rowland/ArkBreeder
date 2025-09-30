package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.CreatureTransfer;
import org.example.backend.Service.CreatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/creatures")
public class CreatureController {

    private final CreatureService creatureService;
//    Makes a new base creature with it allowing for the color regions
    @PostMapping("/new-species")
    public ResponseEntity<?> newCreature(@RequestBody CreatureTransfer species) {
        return new ResponseEntity<>(creatureService.createCreature(species), HttpStatus.OK);
    }


    @GetMapping("/current-species")
    public ResponseEntity<?> getCurrentSpecies(@RequestBody String creatureName) {
        System.out.println(creatureName);
        return new ResponseEntity<>(creatureService.getCreature(creatureName), HttpStatus.OK);
    }

//    Grab a list of all the species added to the DB
    @GetMapping("/grab-all")
    public ResponseEntity<?> getBaseDinoList() {
        return new ResponseEntity<>(creatureService.getCreatures(), HttpStatus.OK);
    }

    @PostMapping("/species-list")
    public ResponseEntity<?> newCreatureList(@RequestBody List<CreatureTransfer> creatures) {
        return new ResponseEntity<>(creatureService.createCreatureList(creatures), HttpStatus.OK);
    }


}
