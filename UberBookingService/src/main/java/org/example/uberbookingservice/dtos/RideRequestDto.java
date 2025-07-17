package org.example.uberbookingservice.dtos;

import lombok.*;
import org.example.uberentityservice.models.ExactLocation;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
    private Long passengerId;
    private org.example.uberentityservice.models.ExactLocation startLocation;
    private ExactLocation endLocation;
    private List<Long> nearByDriverIds;
}
