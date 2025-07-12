package org.example.uberbookingservice.dtos;

import lombok.*;
import org.example.uberentityservice.models.Driver;
import org.example.uberentityservice.models.ExactLocation;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDto {

    private long passengerId;
    private ExactLocation startLocation;
    private ExactLocation endLocation;

}
