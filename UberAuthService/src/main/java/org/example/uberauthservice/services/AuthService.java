package org.example.uberauthservice.services;

import org.example.uberauthservice.dtos.PassengerDto;
import org.example.uberauthservice.dtos.PassengerSignupRequestDto;
import org.example.uberauthservice.models.Passenger;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public PassengerDto sigunpPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger = Passenger.builder().email(passengerSignupRequestDto.getEmail()).name(passengerSignupRequestDto.getName()).password(passengerSignupRequestDto.getPassword()).phoneNumber(passengerSignupRequestDto.getPhoneNumber()).build();
        // TODO: Encrypt password
    }
}
