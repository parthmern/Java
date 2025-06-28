package org.example.uberauthservice.controllers;

import org.example.uberauthservice.dtos.PassengerSignupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> singUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        System.out.println(passengerSignupRequestDto.getEmail());
        return null;
    }

}
