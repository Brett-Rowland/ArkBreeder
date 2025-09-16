package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.Domains.BreedingLine;
import org.example.backend.Service.BreedingLinesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/breeding-line")
public class BreedingLinesController {

    private BreedingLinesService breedingLinesService;

//    Create a Breeding Line
    @PostMapping("/{token}/create/{creatureId}")
    public ResponseEntity<?> createLine(@PathVariable Long token, @PathVariable Long creatureId ,@RequestBody BreedingLine breedingLine) {
        breedingLinesService.createLine(breedingLine, token, creatureId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    Rename a Breeding Line
    @PutMapping("/{lineId}/rename")
    public ResponseEntity<?> renameLine(@PathVariable Long lineId,@RequestBody String newName) {
        breedingLinesService.renameLine(lineId, newName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    Get Specific Line Metadata
    @GetMapping("/{lineId}")
    public ResponseEntity<?> getLineMetadata(@PathVariable Long lineId) {
        return new ResponseEntity<>(breedingLinesService.grabLine(lineId), HttpStatus.OK);
    }

//    Get Specific Line Dinosaurs
    @GetMapping("/{lineId}/dinosaurs")
    public ResponseEntity<?> grabDinos(@PathVariable Long lineId) {
        return new ResponseEntity<>(breedingLinesService.grabDinosaurs(lineId), HttpStatus.OK);
    }

//    Delete a breeding Line
    @DeleteMapping("/{lineId}/delete")
    public  ResponseEntity<?> deleteLine(@PathVariable Long lineId) {
        breedingLinesService.deleteLine(lineId);
        return new  ResponseEntity<>(HttpStatus.OK);
    }





}
