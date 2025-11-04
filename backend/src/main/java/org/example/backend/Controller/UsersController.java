package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.example.backend.Domains.Users;
import org.example.backend.Service.BreedingLinesService;
import org.example.backend.Service.PresetsService;
import org.example.backend.Service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173") // Will need to put in the port for the front end here
public class UsersController {

    private final UsersService usersService;
    private final BreedingLinesService breedingLinesService;

    //    Create an Account
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Users user) {
        try {
            return new ResponseEntity<>(usersService.create(user), HttpStatus.OK);
        }catch (RuntimeException e){
//            Error if the username is taken
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    Delete the account
    @DeleteMapping("/{token}/delete")
    public ResponseEntity<?> deleteAccount(@PathVariable Long token) {
        try {
            usersService.delete(token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user ) {
        try{
            return new ResponseEntity<>(usersService.login(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    Get All breeding lines
    @GetMapping("/{token}/breeding-lines")
    public ResponseEntity<?> getBreedingLines(@PathVariable Long token) {
        return new ResponseEntity<>(breedingLinesService.grabLines(token), HttpStatus.OK);
    }

//    Make a users grab that gets a list of all users that have made an account for you





}
