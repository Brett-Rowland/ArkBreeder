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


//    This is the function that is getting all unvalidated Creatures
//    This is used for the
    @GetMapping("/unvalidated_list")
    public ResponseEntity<?> unvalidatedCreatures() {
        return new ResponseEntity<>(creatureService.getUnvalidatedCreatures(), HttpStatus.OK);
    }

//    This is the Function that is turning the switch on saying that it is validated then grabbing the list again
//    For my personal use
    @PutMapping("/validate/{creatureID}")
    public ResponseEntity<?> updateValidation(@PathVariable("creatureID") long creatureID) {
        try{
            creatureService.updateValidation(creatureID);
            return new ResponseEntity<>(creatureService.getUnvalidatedCreatures(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//  This is the command that is switching all of the stat types within the validation process
    @GetMapping("/validation/{creatureID}")
    public ResponseEntity<?> getCreatureValidation(@PathVariable("creatureID") long creatureID) {
        return new ResponseEntity<>(creatureService.getCreatureStatTypes(creatureID), HttpStatus.OK);
    }

//  Calculates the points added in automatically so I dont have to do it manually
    @PostMapping("/validation/cal")
    public ResponseEntity<?> getValidationCalculation(@RequestBody ValidationInput validationInput) {
        System.out.println(validationInput.getCreatureId());
        return new ResponseEntity<>(computationService.validation(validationInput), HttpStatus.OK);
    }
}
