package org.example.uberauthservice.controllers;

import org.example.uberauthservice.dtos.PassengerDto;
import org.example.uberauthservice.dtos.PassengerSignupRequestDto;
import org.example.uberauthservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> singUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        System.out.println(passengerSignupRequestDto.getEmail());
        PassengerDto res = authService.sigunpPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
