package org.example.uberentityservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(indexes = {
//        @Index(columnList = "driver_id")
//})

public class Booking extends BaseModel {

    @Enumerated(value = EnumType.STRING)
    private BookingStatus bookingStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;

    // n : 1 -> booking : driver ( a booking has one driver )
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Passenger passenger;

    @OneToOne(cascade = CascadeType.ALL)
    private ExactLocation startLocation;

    @OneToOne(cascade = CascadeType.ALL)
    private ExactLocation endLocation;

}
