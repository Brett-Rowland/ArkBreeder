package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.Dinosaur;
import org.example.backend.Service.DinosaurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/dinosaur")

public class DinosaurController {


    private DinosaurService dinosaurService;


//    Create a Dinosaur
    @PostMapping("/{lineId}/create")
    public ResponseEntity<?> createDinosaur(@PathVariable Long lineId, @RequestBody Dinosaur dinosaur) {
        dinosaurService.createDinosaur(dinosaur, lineId);
        return  ResponseEntity.ok().build();
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



}
