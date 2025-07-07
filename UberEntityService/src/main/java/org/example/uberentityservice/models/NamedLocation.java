package org.example.uberentityservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Setter
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class NamedLocation extends BaseModel {

    @OneToOne
    private ExactLocation exactLocation;

    private String name;
    private String zipcode;
    private String city;
    private String state;
    private String country;
}
