package org.example.uberentityservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DBConstants extends BaseModel{

    @Column(unique = true, nullable = false)
    private String name;

    private String value;
}
