package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.ArkColors;
import org.example.backend.Service.ColorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@AllArgsConstructor

public class ColorsController {

    private final ColorsService colorsService;


// Get
    @GetMapping("/{colorId}")
    ResponseEntity<ArkColors> getColor(@PathVariable long colorId) {
       return new ResponseEntity<>(colorsService.getColor(colorId), HttpStatus.OK);
    }

// Post
    @PostMapping
    ResponseEntity<?> createColor(@RequestBody ArkColors arkColors) {
        colorsService.createColor(arkColors);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

// Update
    @PutMapping("/{colorId}")
    ResponseEntity<?> updateColor(@PathVariable long colorId, @RequestBody ArkColors arkColors) throws Exception {
        colorsService.updateColor(arkColors, colorId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

// Delete
    @DeleteMapping("/{colorId}")
    ResponseEntity<?> deleteColor(@PathVariable long colorId) {
        colorsService.deleteColor(colorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//  All
    @GetMapping
    ResponseEntity<List<ArkColors>> getAllColors() {
        return new ResponseEntity<>(colorsService.getAllColors(), HttpStatus.OK);
    }




}
