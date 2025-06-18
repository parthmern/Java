package com.example.uberreviewservice.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@Getter // lombok - adding all getters func
@Setter // lombok - adding all setter func
@Builder   // lombok - builder pattern
@NoArgsConstructor  // lombok - default constructor without args ( needed to builder pattern/lombok
@AllArgsConstructor // lombok - all arg const. ( needed to builder pattern/lombok
@EntityListeners(AuditingEntityListener.class)  // to solve error of null date with createdAt,updatedAt

@Entity
@Table(name = "bookingreview") // optional else class_name = table_name
public class Review {
    @Id     // this annotation makes PK of table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY means auto_increment
    private Long id;

    @Column(nullable = false)
    private String content;

    private Double rating;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // format of date obj to be stored ex. TIME, DATE, TIMESTAMP
    @CreatedDate    // handle it only when object creation
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate   // only handle it for object update
    private Date updatedAt;

    @Override
    public String toString(){
        return "Review: " + this.content + " " + this.rating + " " + this.createdAt;
    }

}
// new review(content, rating, createdAt, updatedAt)