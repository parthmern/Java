package com.example.uberreviewservice.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;


@Getter // lombok - adding all getters func
@Setter // lombok - adding all setter func
@Builder   // lombok - builder pattern
@NoArgsConstructor  // lombok - default constructor without args ( needed to builder pattern/lombok
@AllArgsConstructor // lombok - all arg const. ( needed to builder pattern/lombok

@Entity
@Table(name = "bookingreview") // optional else class_name = table_name
public class Review extends BaseModel {

    @Column(nullable = false)
    private String content;

    private Double rating;

    @Override
    public String toString(){
        return "Review: " + this.content + " " + this.rating + " " + this.createdAt;
    }

}
// new review(content, rating, createdAt, updatedAt)