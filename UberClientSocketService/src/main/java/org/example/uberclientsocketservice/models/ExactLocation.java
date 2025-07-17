package org.example.uberclientsocketservice.models;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExactLocation {
    private Double latitude;
    private Double longitude;
}
