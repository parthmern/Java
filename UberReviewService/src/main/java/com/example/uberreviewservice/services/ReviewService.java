package com.example.uberreviewservice.services;

import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService implements CommandLineRunner {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("****************"); // spring boot going to call this func first without making obj
        // Review R = new Review();
        Review r = Review.builder().content("new good").rating(4.0).build();
        System.out.println(r);
        reviewRepository.save(r);   // executes sql query

        List<Review> reviews = reviewRepository.findAll();

        for(Review review: reviews){
            System.out.println(r.getContent());
        }
    }
}
