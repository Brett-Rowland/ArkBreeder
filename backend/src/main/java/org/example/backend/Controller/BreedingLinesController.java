package org.example.backend.Controller;

import lombok.AllArgsConstructor;
import org.example.backend.Service.BreedingLinesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 *  REST Controller meant for managing Breeding Lines.
 *
 *  A Breeding Line is a group of dinosaurs bred together to optimize stats and/or colors.
 *
 *  Responsibilities:
 *  - Create breeding lines
 *  - Delete breeding lines
 *  - Retrieve breeding lines
 *  - Rename breeding lines
 *  - Update server tied to the line
 *
 *  This controller does not perform stat calculations or mutation math.
 *  All business logic is delegated to {@link BreedingLinesService}.
 *
 *  All endpoints assume the user is authenticated.
 */

@RestController
@AllArgsConstructor
@RequestMapping("/breeding-line")
@CrossOrigin(origins = "http://localhost:5173")
public class BreedingLinesController {

    private BreedingLinesService breedingLinesService;

    /**
     *  Creates a new breeding line
     *  Path Variables:
     *  - token: authentication token identifying the user
     *  - creatureId: ID of the creature associated with this breeding line
     *  - serverId: ID of the server configuration applied to the line
     *
     *  Request body:
     *  - lineNickname: Nickname for this Breeding Line
     *
     *  Returns:
     *  - 201 CREATED with a {@link org.example.backend.DTOs.BreedingLineTransfer} containing:
     *      - lineId
     *      - creatureName
     *      - tracked stat types
     *      - line nickname
     *  Error cases:
     *  - 400 BAD REQUEST if the breeding line cannot be created
     */

    @PostMapping("/{token}/create/{creatureId}/{serverId}")
    public ResponseEntity<?> createLine(@PathVariable Long token, @PathVariable Long creatureId,@RequestBody String lineNickname, @PathVariable Long serverId) {
        try {
            return new ResponseEntity<>(breedingLinesService.createLine(lineNickname, token, creatureId, serverId) ,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Line Not Created",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  Breeding Lines Page Setup
     *
     *  Path Variables:
     *  - token: authentication token identifying the user
     *  - limit: Handle pagination
     *
     *  Returns:
     *  - 200 OK with a breedingLinePageDTO
     *          - Breeding Line List
     *          - Setting List
     *          - Creature List
     *  Error cases:
     *  - 500 INTERNAL SERVER ERROR if an unexpected error occurs
     * */
    @GetMapping("/{token}/list/{limit}")
    public ResponseEntity<?> breedingLinePage(@PathVariable Long token, @PathVariable Integer limit){
        try{
            return new ResponseEntity<>(breedingLinesService.breedingPageSetup(token, limit), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     *  Renames a Breeding Line
     *
     *  Path Variables:
     *  - lineId: Breeding Line ID
     *
     *  Request body:
     *  - newName: New display name for the line
     *
     *  Returns:
     *  - 200 OK
     *  Error cases:
     * */
    @PutMapping("/{lineId}/rename")
    public ResponseEntity<?> renameLine(@PathVariable Long lineId,@RequestBody String newName) {
        breedingLinesService.renameLine(lineId, newName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     *  Deletes a Breeding Line
     *
     *  Path Variables:
     *  - lineId: Breeding Line ID
     *
     *  Returns:
     *  - 200 OK: Deletion flag flipped on within Breeding Line
     *
     * */
    @DeleteMapping("/{lineId}/delete")
    public  ResponseEntity<?> deleteLine(@PathVariable Long lineId) {
        breedingLinesService.deleteLine(lineId);
        return new  ResponseEntity<>(HttpStatus.OK);
    }


    /**
     *  Changes Server Breeding Line is in
     *
     *  Path Variables:
     *  - lineId: Breeding Line ID
     *  - serverId: Server ID
     *
     *  Returns:
     *  - 200 OK: With successful update
     * */
    @PutMapping("/{lineId}/settings/{serverId}")
    public ResponseEntity<?> updateSettings(@PathVariable Long lineId, @PathVariable Long serverId){
        breedingLinesService.updateSettings(lineId, serverId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
