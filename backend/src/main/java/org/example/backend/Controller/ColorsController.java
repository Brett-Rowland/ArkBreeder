package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.ArkColors;
import org.example.backend.Service.ColorsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  REST Controller meant for managing Ark Colors.
 *
 *  An Ark Color is a color that has the hexCode and the specific number tied to it in game
 *
 *  Responsibilities:
 *  - Creating Colors
 *  - Deleting Colors
 *  - Updating Colors
 *  - Getting All Colors
 *
 *  All business logic is delegated to {@link ColorsService}.
 *
 *  All endpoints assume the user is authenticated.
 */
@RestController
@RequestMapping("/colors")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ColorsController {

    private final ColorsService colorsService;

    /**
     *  Updates a color
     *
     *  Path Variables:
     *  - colorId: Color ID
     *
     *  Request Body:
     *  - arkColor: Color Object
     *      - Color ID
     *      - Color Name
     *      - Color hexCode
     *
     *  Returns:
     *  - 200 OK
     */
    @PutMapping("/{colorId}")
    ResponseEntity<?> updateColor(@PathVariable long colorId, @RequestBody ArkColors arkColor){
        colorsService.updateColor(arkColor, colorId);
        return ResponseEntity.ok().build();
    }


    /**
     *  Deletes a color
     *
     *  Path Variables:
     *  - colorId: Color ID
     *
     *  Returns:
     *  - 200 OK
     * */
    @DeleteMapping("/{colorId}")
    ResponseEntity<?> deleteColor(@PathVariable long colorId) {
        colorsService.deleteColor(colorId);
        return ResponseEntity.ok().build();
    }

    /**
     *  Grab All Colors
     *
     *  Returns:
     *  - 200 OK List ArkColors
     *      - Color ID
     *      - Color Name
     *      - Color hexCode
     * */
    @GetMapping
    ResponseEntity<List<ArkColors>> getAllColors() {
        return new ResponseEntity<>(colorsService.getAllColors(), HttpStatus.OK);
    }

    /**
     *  Creates all colors
     *
     *  Request Body:
     *  - arkColors: List Color Object
     *      - Color ID
     *      - Color Name
     *      - Color hexCode
     *
     *  Returns:
     *  - 201 CREATED
     * */
    @PostMapping("/create")
    ResponseEntity<?> addColors(@RequestBody List<ArkColors> arkColors) {
        colorsService.createColorList(arkColors);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
