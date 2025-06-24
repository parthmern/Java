package com.example.uberreviewservice.services;

import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.Driver;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.BookingRepository;
import com.example.uberreviewservice.repositories.DriverRepository;
import com.example.uberreviewservice.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService implements CommandLineRunner {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private DriverRepository driverRepository;

    @Override
    @Transactional
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

//        Optional<Driver> d = driverRepository.rawFindByIdAndLicenseNumber(1L, "DJid3003");
//
//        Optional<Driver> dr = driverRepository.hqaFindByIdAndLicenseNumber(1L, "DJid3003");
//        System.out.println(dr.get().getName());

        List<Long> driverIds = new ArrayList<>(Arrays.asList(1L,2L,3L,4L,5L));
        List<Driver> drivers = driverRepository.findAllByIdIn(driverIds);

        List<Booking> bookings = bookingRepository.findAllByDriverIn(drivers);

//        for(Driver driver : drivers){
//            List<Booking> bookings = driver.getBookings();
//            bookings.forEach(booking -> System.out.println(
//                    booking.getId()
//            ));
//        }

    }
}
