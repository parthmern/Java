package org.example.uberlocationservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyDriversRequestDto {
    Double latitude;
    Double longitude;
}
