package org.example.uberauthservice.repositories;

import org.example.uberentityservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
//    Optional<Driver> findB
}
