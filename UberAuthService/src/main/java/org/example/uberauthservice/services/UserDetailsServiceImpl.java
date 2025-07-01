package org.example.uberauthservice.services;

import org.example.uberauthservice.helper.AuthPassengerDetails;
import org.example.uberauthservice.models.Passenger;
import org.example.uberauthservice.repositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PassengerRepository passengerRepository;

    public UserDetailsServiceImpl(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);
        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find user/passenger by given email");
        }
    }
}
