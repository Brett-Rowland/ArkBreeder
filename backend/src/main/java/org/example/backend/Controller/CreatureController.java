package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.CreatureInput;
import org.example.backend.DTOs.ValidationInput;
import org.example.backend.Domains.Creature;
import org.example.backend.Service.ComputationService;
import org.example.backend.Service.CreatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/creatures")
public class CreatureController {

    private final CreatureService creatureService;
    private final ComputationService computationService;
//    Makes a new base creature with it allowing for the color regions
    @PostMapping("/new-species")
    public ResponseEntity<?> newCreature(@RequestBody CreatureInput species) {
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
    public ResponseEntity<?> newCreatureList(@RequestBody List<CreatureInput> creatures) {
        return new ResponseEntity<>(creatureService.createCreatureList(creatures), HttpStatus.OK);
    }

    @PostMapping("/species-list-old")
    public ResponseEntity<?> oldCreatureList(@RequestBody List<Creature> creatures){
        return new ResponseEntity<>(creatureService.createCreatureListOld(creatures), HttpStatus.OK);
    }

    @GetMapping("/validation-list")
    public ResponseEntity<?> getValidationList() {
        return new ResponseEntity<>(creatureService.getCreatureValidation(), HttpStatus.OK);
    }

    @PutMapping("/validate/{creatureID}")
    public ResponseEntity<?> validateCreature(@PathVariable("creatureID") long creatureID) {
        try{
            creatureService.updateValidation(creatureID);
            return new ResponseEntity<>(creatureService.getCreatureValidation(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/validation/{creatureID}")
    public ResponseEntity<?> getCreatureValidation(@PathVariable("creatureID") long creatureID) {
        return new ResponseEntity<>(creatureService.getCreatureStatTypes(creatureID), HttpStatus.OK);
    }


    @PostMapping("/validation/cal")
    public ResponseEntity<?> getValidationCalc(@RequestBody ValidationInput validationInput) {
        System.out.println(validationInput.getCreatureId());
        return new ResponseEntity<>(computationService.validation(validationInput), HttpStatus.OK);
    }
}
