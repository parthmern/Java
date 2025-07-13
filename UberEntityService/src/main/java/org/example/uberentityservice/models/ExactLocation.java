package org.example.uberentityservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Setter
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ExactLocation extends BaseModel {
    private Double latitude;
    private Double longitude;
}
