package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.ColorRegions;
import org.example.backend.Service.RegionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regions")
@AllArgsConstructor

public class ColorRegionController {
    RegionsService regionsService;

//  Post

    @PostMapping("/creatures/{creatureId}")
    ResponseEntity<?> createColorRegion(@PathVariable("creatureId") long creatureId, @RequestBody ColorRegions regions) throws Exception {
        regionsService.createColorRegion(regions, creatureId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
//      Get
    @GetMapping("/{regionId}")
    ResponseEntity<ColorRegions>  grabColorRegion(@PathVariable("regionId") long colorRegionID){
        return new ResponseEntity<>(regionsService.grabColorRegion(colorRegionID), HttpStatus.OK);
    }

//    Update
    @PutMapping("/{regionId}")
    ResponseEntity<?> updateColorRegion(@PathVariable("regionId") long colorRegionID, @RequestBody ColorRegions regions){
        regionsService.updateColorRegion(regions, colorRegionID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//    Delete
    @DeleteMapping("/{regionId}")
    ResponseEntity<?> deleteColorRegion(@PathVariable("regionId") long colorRegionID){
        regionsService.deleteColorRegion(colorRegionID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    Get All Color Regions
    @GetMapping("/all")
    ResponseEntity<?> getAllColorRegions(){
        return new ResponseEntity<>(regionsService.getAllColorRegions(), HttpStatus.OK);
    }

//    Get Specific Dino
    @GetMapping("/creatures/{creatureID}")
    ResponseEntity<?> getAllColorRegionsByCreature(@PathVariable("creatureID") long creatureID){
        return new ResponseEntity<>(regionsService.creaturesColorRegions(creatureID), HttpStatus.OK);
    }





}
