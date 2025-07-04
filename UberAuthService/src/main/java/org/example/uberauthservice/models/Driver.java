package org.example.uberauthservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor

public class Driver extends BaseModel {

    private String name;

    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    // 1 : n -> driver : booking   (a driver has many booking)
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Booking> bookings = new ArrayList<>();
}

