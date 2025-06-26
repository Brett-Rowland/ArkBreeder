package org.example.backend.Controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return new  ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
