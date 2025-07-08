package org.example.uberentityservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PassengerReview extends Review {
    private String passengeerReviewContent;

    private String passengerRating;

}
