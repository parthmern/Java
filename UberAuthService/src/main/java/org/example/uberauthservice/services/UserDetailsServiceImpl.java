package org.example.uberauthservice.services;

import org.example.uberauthservice.helper.AuthPassengerDetails;
import org.example.uberauthservice.models.Passenger;
import org.example.uberauthservice.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// this class is responsible for loading the user in the form of UserDetais object
// as part of UserDetailsService from spring security
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load user by username");
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);
        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find user/passenger by given email");
        }
    }
}
