package org.example.uberbookingservice.repositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.transaction.Transactional;
import org.example.uberentityservice.models.Booking;
import org.example.uberentityservice.models.BookingStatus;
import org.example.uberentityservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Modifying
    @Transactional
    @JsonIgnore
    @Query("UPDATE Booking b SET b.bookingStatus = :status, b.driver = :driver  WHERE b.id = :id")
    void updateBookingStatusAndDriverById(@Param("id") Long id, @Param("status") BookingStatus status, @Param("driver") Driver driver);

}
