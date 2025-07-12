package org.example.uberbookingservice.repositories;

import org.example.uberentityservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
