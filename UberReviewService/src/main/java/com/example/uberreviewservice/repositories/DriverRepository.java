package com.example.uberreviewservice.repositories;

import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByIdAndLicenseNumber(long id, String license_number);

    @Query(nativeQuery = true, value = "SELECT * FROM driver WHERE id= :id AND license_number = :license" )
    Optional<Driver> rawFindByIdAndLicenseNumber(Long id, String license);

    @Query("SELECT d FROM Driver d WHERE d.id = :id AND d.licenseNumber = :license")
    Optional<Driver> hqaFindByIdAndLicenseNumber(Long id, String license);

    List<Driver> findAllByIdIn(List<Long> driverIds);

}
