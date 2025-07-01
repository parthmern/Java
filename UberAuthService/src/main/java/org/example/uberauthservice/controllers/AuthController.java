package org.example.uberauthservice.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.uberauthservice.dtos.AuthRequestDto;
import org.example.uberauthservice.dtos.AuthResponseDto;
import org.example.uberauthservice.dtos.PassengerDto;
import org.example.uberauthservice.dtos.PassengerSignupRequestDto;
import org.example.uberauthservice.services.AuthService;
import org.example.uberauthservice.services.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private AuthService authService;
    private final JwtService jwtService;

    AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtService jwtService){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> singUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        System.out.println(passengerSignupRequestDto.getEmail());
        PassengerDto res = authService.sigunpPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        System.out.println("/signin/passenger hitted" + authRequestDto.getEmail() + authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            String jwtToken = jwtService.createToken(authRequestDto.getEmail());
            ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                                    .httpOnly(true)
                                    .secure(false)
                                    .path("/")
                                    .maxAge(3600)
                                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Auth not successful", HttpStatus.OK);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> isValidate(HttpServletRequest request){
        List<Cookie> cookies = List.of(request.getCookies());
        if(!cookies.isEmpty()){
            for (Cookie cookie : cookies){
                System.out.println(cookie.getName() + " " + cookie.getValue());
            }
        }else {
            System.out.println("cookie is empty");
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
