package org.example.uberbookingservice.dtos;

import lombok.*;
import org.example.uberentityservice.models.BookingStatus;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateBookingRequestDto {
    private String bookingStatus;
    private Optional<Long> driverId;
    private Long bookingId;
}
