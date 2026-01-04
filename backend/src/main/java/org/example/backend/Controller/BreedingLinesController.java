package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.DTOs.BreedingLineTransfer;
import org.example.backend.Domains.BreedingLine;
import org.example.backend.Service.BreedingLinesService;
import org.example.backend.Service.ComputationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/breeding-line")
@CrossOrigin(origins = "http://localhost:5173")
public class BreedingLinesController {

    private BreedingLinesService breedingLinesService;

//    Create a Breeding Line
    @PostMapping("/{token}/create/{creatureId}/{serverId}")
    public ResponseEntity<?> createLine(@PathVariable Long token, @PathVariable Long creatureId ,@RequestBody String lineNickname, @PathVariable Long serverId) {
        try {
            return new ResponseEntity<>(breedingLinesService.createLine(lineNickname, token, creatureId, serverId) ,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Line Not Created",HttpStatus.BAD_REQUEST);
        }
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

//    Get List of lines
    @GetMapping("/{token}/list/{limit}")
    public ResponseEntity<?> breedingLineList(@PathVariable Long token, @PathVariable Integer limit){
        System.out.println(limit);
        try{
            return new ResponseEntity<>(breedingLinesService.breedingPageSetup(token, limit), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping("/{lineId}/computed")
    public ResponseEntity<?> getLineComputed(@PathVariable Long lineId){
        return new ResponseEntity<>(breedingLinesService.lineComputation(lineId), HttpStatus.OK);
    }

    @PutMapping("/{lineId}/settings/{serverId}")
    public ResponseEntity<?> updateSettings(@PathVariable Long lineId, @PathVariable Long serverId){
        return new ResponseEntity(breedingLinesService.updateSettings(lineId, serverId), HttpStatus.OK);
    }

}
