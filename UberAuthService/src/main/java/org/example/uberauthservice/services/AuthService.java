package org.example.uberauthservice.services;

import org.example.uberauthservice.dtos.PassengerDto;
import org.example.uberauthservice.dtos.PassengerSignupRequestDto;
import org.example.uberauthservice.repositories.PassengerRepository;
import org.example.uberentityservice.models.Passenger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    PassengerRepository passengerRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto sigunpPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger = Passenger.builder()
                                .email(passengerSignupRequestDto.getEmail())
                                .name(passengerSignupRequestDto.getName())
                                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        PassengerDto response = PassengerDto.from(newPassenger);
        return response;
    }
}
