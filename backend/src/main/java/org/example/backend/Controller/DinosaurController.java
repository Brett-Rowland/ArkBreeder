package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.DTOs.DinosaurInput;
import org.example.backend.DTOs.ValidationInput;
import org.example.backend.Domains.Dinosaur;
import org.example.backend.Service.ComputationService;
import org.example.backend.Service.DinosaurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/dinosaur")

public class DinosaurController {


    private DinosaurService dinosaurService;
    private ComputationService computationService;


//    Create a Dinosaur
    @PostMapping("/{lineId}/create")
    public ResponseEntity<?> createDinosaur(@PathVariable Long lineId, @RequestBody DinosaurInput dinosaur) throws Exception {
        try {
            dinosaurService.createDinosaur(dinosaur, lineId);
            return  ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//    Update Dinosaur
    @PutMapping("/{dinoId}/update")
    public ResponseEntity<?> updateDinosaur(@PathVariable Long dinoId, @RequestBody Dinosaur dinosaur) {
        dinosaurService.updateDinosaur(dinosaur, dinoId);
        return  ResponseEntity.ok().build();

    }
//    Delete Dinosaur

    @DeleteMapping("/{dinoId}/delete")
    public ResponseEntity<?> deleteDinosaur(@PathVariable Long dinoId) {
        dinosaurService.deleteDinosaur(dinoId);
        return  ResponseEntity.ok().build();
    }

//    Rename Dinosaur
    @PutMapping("/{dinoId}/rename")
    public ResponseEntity<?> renameDinosaur(@PathVariable Long dinoId, @RequestBody String newName) {
        dinosaurService.renameDinosaur(dinoId, newName);
        return  ResponseEntity.ok().build();
    }


//    Get Specific Dino
    @GetMapping("/{dinoId}/grab")
    public ResponseEntity<?> grabDinosaur(@PathVariable Long dinoId) {
        return new ResponseEntity<>(dinosaurService.grabDino(dinoId), HttpStatus.OK);
    }


    @GetMapping("/{dinoId}/compute")
    public ResponseEntity<?> getDinoCompute(@PathVariable Long dinoId){
        return new ResponseEntity<>(computationService.dinoComputation(dinoId), HttpStatus.OK);
    }

}
