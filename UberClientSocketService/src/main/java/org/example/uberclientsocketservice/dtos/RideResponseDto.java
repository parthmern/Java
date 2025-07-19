package org.example.uberclientsocketservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideResponseDto {
    public boolean response;
    public Long driverId;
    public Long bookingId;
}
