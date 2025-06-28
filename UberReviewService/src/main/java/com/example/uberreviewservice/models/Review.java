package com.example.uberreviewservice.models;
import jakarta.persistence.*;
import lombok.*;

@Getter // lombok - adding all getters func
@Setter // lombok - adding all setter func
@Builder   // lombok - builder pattern
@NoArgsConstructor  // lombok - default constructor without args ( needed to builder pattern/lombok
@AllArgsConstructor // lombok - all arg const. ( needed to builder pattern/lombok

@Entity
@Table(name = "booking_review") // optional else class_name = table_name

@Inheritance(strategy = InheritanceType.JOINED)

public class Review extends BaseModel {

    @Column(nullable = false)
    private String content;

    private Double rating;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Booking booking; // we have defined a 1:1 relationship between booking and review

    @Override
    public String toString(){
        return "Review: " + this.content + " " + this.rating + " " + this.createdAt;
    }

}
// new review(content, rating, createdAt, updatedAt)