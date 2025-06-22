package com.example.uberreviewservice.services;

import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.Driver;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.BookingRepository;
import com.example.uberreviewservice.repositories.DriverRepository;
import com.example.uberreviewservice.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements CommandLineRunner {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("****************"); // spring boot going to call this func first without making obj
//        // Review R = new Review();
//        Review r = Review.builder().content("amazing").rating(4.0).build();
//        Booking b = Booking.builder().review(r).endTime(new Date()).build();
////        reviewRepository.save(r);
//       bookingRepository.save(b);
//
//        System.out.println("REVIEW=>" + r);
//        System.out.println("BookingReview=>"+ b);
//
//        List<Review> reviews = reviewRepository.findAll();
//
//        for(Review review: reviews){
//            System.out.println(r.getContent());
//        }

//        Optional<Driver> driver = driverRepository.findByIdAndLicenseNumber(1L, "DJid3003");
//
//        if(driver.isPresent()){
//            System.out.println(driver.get().getName());
//        }

        Optional<Driver> d = driverRepository.rawFindByIdAndLicenseNumber(1L, "DJid3003");

        Optional<Driver> dr = driverRepository.hqaFindByIdAndLicenseNumber(1L, "DJid3003");
        System.out.println(dr.get().getName());

    }
}
