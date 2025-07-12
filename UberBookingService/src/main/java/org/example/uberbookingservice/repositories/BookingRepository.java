package org.example.uberbookingservice.repositories;

import org.example.uberentityservice.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
