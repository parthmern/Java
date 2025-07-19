package org.example.uberclientsocketservice.dtos;

import lombok.*;
import org.example.uberclientsocketservice.models.ExactLocation;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
    private Long bookingId;
    private Long passengerId;
    private ExactLocation startLocation;
    private ExactLocation endLocation;
    private List<Long> nearByDriverIds;
}
