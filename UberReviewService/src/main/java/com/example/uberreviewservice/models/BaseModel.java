package com.example.uberreviewservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)  // to solve error of null date with createdAt,updatedAt
@MappedSuperclass
public abstract class BaseModel {

    @Id     // this annotation makes PK of table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY means auto_increment
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // format of date obj to be stored ex. TIME, DATE, TIMESTAMP
    @CreatedDate    // handle it only when object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate   // only handle it for object update
    protected Date updatedAt;

}
