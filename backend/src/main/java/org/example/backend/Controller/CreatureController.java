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

/**
 * REST controller for managing Creature (species) definitions.
 *
 * A Creature represents a base species definition used throughout the application
 * (e.g., Therizino, Rex). Creature definitions are used to drive:
 * - base stat configuration and stat type metadata
 * - color region definitions
 * - validation workflows to confirm correctness of configured data
 *
 * Responsibilities:
 * - Create single or bulk creature definitions
 * - Retrieve creature definitions (single or list)
 * - Support validation workflow (list unvalidated, mark validated, view stat types)
 * - Run validation calculations via {@link ComputationService}
 *
 * All business logic is delegated to {@link CreatureService} and {@link ComputationService}.
 * All endpoints assume the user is authenticated.
 */

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/creatures")
public class CreatureController {

    private final CreatureService creatureService;
    private final ComputationService computationService;
    /**
     * Creates a new creature (species) definition.
     *
     * Request body:
     * - species: {@link CreatureInput} containing the creature definition fields
     *   (including base stats and color region configuration as applicable)
     *
     * Returns:
     * - 200 OK with the created creature payload
     *
     * Notes:
     * - This endpoint is used to add a new base species into the database.
     */    @PostMapping("/new-creature")
    public ResponseEntity<?> newCreature(@RequestBody CreatureInput species) {
        return new ResponseEntity<>(creatureService.createCreature(species), HttpStatus.CREATED);
    }

    /**
     * Retrieves a creature (species) definition by name.
     *
     * Query parameters:
     * - name: name of the creature to retrieve
     *
     * Returns:
     * - 200 OK with the creature definition if found
     */
    @GetMapping
    public ResponseEntity<?> getCurrentSpecies(@RequestParam String name) {
        return new ResponseEntity<>(creatureService.getCreature(name), HttpStatus.OK);
    }

    /**
     * Retrieves a list of all creature (species) definitions stored in the database.
     *
     * Returns:
     * - 200 OK with a list of creatures
     */
    @GetMapping("/grab-all")
    public ResponseEntity<?> getBaseDinoList() {
        return new ResponseEntity<>(creatureService.getCreatures(), HttpStatus.OK);
    }

    /**
     * Creates multiple creature (species) definitions in a single request.
     *
     * Request body:
     * - creatures: list of {@link CreatureInput} objects
     *
     * Returns:
     * - 200 OK with the created creature list payload (or creation result)
     */
    @PostMapping("/creature-list")
    public ResponseEntity<?> newCreatureList(@RequestBody List<CreatureInput> creatures) {
        return new ResponseEntity<>(creatureService.createCreatureList(creatures), HttpStatus.OK);
    }

    /**
     * Retrieves all unvalidated creature definitions.
     *
     * This is used during the data validation workflow to identify creatures that
     * still require verification.
     *
     * Returns:
     * - 200 OK with a list of unvalidated creatures
     */
    @GetMapping("/unvalidated_list")
    public ResponseEntity<?> unvalidatedCreatures() {
        return new ResponseEntity<>(creatureService.getUnvalidatedCreatures(), HttpStatus.OK);
    }

    /**
     * Marks a creature definition as validated and returns the updated unvalidated list.
     *
     * Path variables:
     * - creatureID: ID of the creature to mark as validated
     *
     * Returns:
     * - 200 OK with the updated list of unvalidated creatures
     *
     * Error cases:
     * - 400 BAD REQUEST if validation update fails
     *
     * Notes:
     * - This endpoint is primarily used for the internal/personal validation workflow.
     */
    @PutMapping("/validate/{creatureID}")
    public ResponseEntity<?> updateValidation(@PathVariable("creatureID") long creatureID) {
        try{
            creatureService.updateValidation(creatureID);
            return new ResponseEntity<>(creatureService.getUnvalidatedCreatures(),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the stat type configuration for a specific creature during validation.
     *
     * Path variables:
     * - creatureID: ID of the creature being validated
     *
     * Returns:
     * - 200 OK with the creature's stat type list/configuration
     *
     * Notes:
     * - This endpoint supports the UI flow for selecting or reviewing which stat types
     *   apply to the creature during the validation process.
     */
    @GetMapping("/validation/{creatureID}")
    public ResponseEntity<?> getCreatureValidation(@PathVariable("creatureID") long creatureID) {
        return new ResponseEntity<>(creatureService.getCreatureStatTypes(creatureID), HttpStatus.OK);
    }

    /**
     * Runs validation calculations to determine stat point distributions automatically.
     *
     * This endpoint is used to assist validation by computing implied point allocations
     * (so manual calculation is not required).
     *
     * Request body:
     * - validationInput: {@link ValidationInput} containing the inputs required to run
     *   the validation computation (e.g., creatureId and provided stat values)
     *
     * Returns:
     * - 200 OK with computed validation results from {@link ComputationService}
     */
    @PostMapping("/validation/cal")
    public ResponseEntity<?> getValidationCalculation(@RequestBody ValidationInput validationInput) {
        return new ResponseEntity<>(computationService.validation(validationInput), HttpStatus.OK);
    }
}
