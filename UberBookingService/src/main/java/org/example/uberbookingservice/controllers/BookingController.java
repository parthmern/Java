package org.example.uberbookingservice.controllers;

import org.example.uberbookingservice.dtos.CreateBookingDto;
import org.example.uberbookingservice.dtos.CreateBookingResponseDto;
import org.example.uberbookingservice.dtos.UpdateBookingResponseDto;
import org.example.uberbookingservice.dtos.UpdateBookingRequestDto;
import org.example.uberbookingservice.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto createBookingDto){
        return new ResponseEntity<>(bookingService.createBooking(createBookingDto),HttpStatus.CREATED);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingRequestDto updateBookingRequestDto){
        return new ResponseEntity<>(bookingService.updateBooking(updateBookingRequestDto,bookingId),HttpStatus.OK);
    }
}
