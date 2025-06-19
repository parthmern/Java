package com.example.uberreviewservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@PrimaryKeyJoinColumn(name = "driver_review_id")     // Seperate FK
public class DriverReview extends Review {
    private String driverReviewContent;
}
