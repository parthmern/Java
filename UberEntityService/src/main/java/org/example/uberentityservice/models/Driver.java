package org.example.uberentityservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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

    private String aadharCard;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    // 1 : n -> driver : booking   (a driver has many booking)
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Booking> bookings = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private DriverApprovalStatus driverApprovalStatus;

    @OneToOne
    private ExactLocation lastKnownLocation;

    @OneToOne
    private ExactLocation home;

    private String activeCity;

    @DecimalMin(value = "0.0", message = "Rating must be greater than or equal to 0.00")
    @DecimalMax(value = "5.00", message = "Rating must be less than or equal to 5.00")
    private double rating;  // avg. rating
}
