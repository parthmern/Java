package org.example.uberbookingservice.dtos;

import lombok.*;
import org.example.uberentityservice.models.Driver;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponseDto {
    private long bookingId;
    private String bookingStatus;
    private Optional<Driver> driver;
}
