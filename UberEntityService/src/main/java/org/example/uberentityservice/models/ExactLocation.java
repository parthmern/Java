package org.example.uberentityservice.models;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class ExactLocation extends BaseModel {
    private Double latitude;
    private Double longitude;
}
