package org.example.uberbookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = "org.example.uberentityservice.models")
public class UberBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberBookingServiceApplication.class, args);
    }

}
