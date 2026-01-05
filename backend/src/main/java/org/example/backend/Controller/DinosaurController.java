package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.DTOs.DinosaurInput;
import org.example.backend.Service.ComputationService;
import org.example.backend.Service.DinosaurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



/**
 * REST controller for managing Dinosaurs.
 *
 * A Dinosaur represents an individual creature instance within a breeding line
 * (e.g., a specific male/female used for breeding, offspring, or candidates).
 *
 * Responsibilities:
 * - Create dinosaurs within a breeding line
 * - Rename dinosaurs
 * - Soft-delete dinosaurs (same deletion behavior as breeding lines)
 * - Retrieve a dinosaur by ID
 * - Compute derived dinosaur stats/values via {@link ComputationService}
 *
 * All business logic is delegated to {@link DinosaurService} and {@link ComputationService}.
 * All endpoints assume the user is authenticated.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dinosaur")
@CrossOrigin(origins = "http://localhost:5173")
public class DinosaurController {


    private DinosaurService dinosaurService;
    private ComputationService computationService;


    /**
     * Creates a dinosaur within a specific breeding line.
     *
     * Path variables:
     * - lineId: ID of the breeding line this dinosaur will belong to
     *
     * Request body:
     * - dinosaur: {@link DinosaurInput} containing the dinosaur fields
     *   (e.g., name, gender, base stat values/points, colors, parent info, etc. as applicable)
     *
     * Returns:
     * - 200 OK if creation succeeds
     *
     * Error cases:
     * - 400 BAD REQUEST with an error message if creation fails
     */    @PostMapping("/{lineId}/create")
    public ResponseEntity<?> createDinosaur(@PathVariable Long lineId, @RequestBody DinosaurInput dinosaur){
        try {
            dinosaurService.createDinosaur(dinosaur, lineId);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Soft-deletes a dinosaur by ID.
     *
     * Deletion behavior matches breeding lines (a deletion flag is flipped rather than
     * permanently removing the record).
     *
     * Path variables:
     * - dinoId: ID of the dinosaur to delete
     *
     * Returns:
     * - 200 OK if deletion succeeds
     */
    @DeleteMapping("/{dinoId}/delete")
    public ResponseEntity<?> deleteDinosaur(@PathVariable Long dinoId) {
        dinosaurService.deleteDinosaur(dinoId);
        return  ResponseEntity.ok().build();
    }

    /**
     * Renames a dinosaur by ID.
     *
     * Path variables:
     * - dinoId: ID of the dinosaur to rename
     *
     * Request body:
     * - newName: new display name for the dinosaur
     *
     * Returns:
     * - 200 OK if rename succeeds
     */
    @PutMapping("/{dinoId}/rename")
    public ResponseEntity<?> renameDinosaur(@PathVariable Long dinoId, @RequestBody String newName) {
        dinosaurService.renameDinosaur(dinoId, newName);
        return  ResponseEntity.ok().build();
    }


    /**
     * Retrieves a dinosaur by ID.
     *
     * Path variables:
     * - dinoId: ID of the dinosaur to retrieve
     *
     * Returns:
     * - 200 OK with the dinosaur payload
     */
    @GetMapping("/{dinoId}/grab")
    public ResponseEntity<?> grabDinosaur(@PathVariable Long dinoId) {
        return new ResponseEntity<>(dinosaurService.grabDino(dinoId), HttpStatus.OK);
    }


    /**
     * Computes derived values for a specific dinosaur.
     *
     * This endpoint is used when recalculations are needed (e.g., after changing stats,
     * imprinting, mutation points, or server-related computation inputs).
     *
     * Path variables:
     * - dinoId: ID of the dinosaur to compute
     *
     * Returns:
     * - 200 OK with the computed dinosaur results from {@link ComputationService}
     */
    @GetMapping("/{dinoId}/compute")
    public ResponseEntity<?> getDinoCompute(@PathVariable Long dinoId){
        return new ResponseEntity<>(computationService.dinoComputation(dinoId), HttpStatus.OK);
    }

}
