package org.example.uberlocationservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationDto {
    String driverId;
    Double latitude;
    Double longitude;
}
