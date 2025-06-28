package org.example.uberauthservice.repositories;

import org.example.uberauthservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
