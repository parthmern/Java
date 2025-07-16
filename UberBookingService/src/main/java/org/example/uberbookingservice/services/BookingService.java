package org.example.uberbookingservice.services;

import org.example.uberbookingservice.dtos.CreateBookingDto;
import org.example.uberbookingservice.dtos.CreateBookingResponseDto;
import org.example.uberbookingservice.dtos.UpdateBookingRequestDto;
import org.example.uberbookingservice.dtos.UpdateBookingResponseDto;


public interface BookingService {
    CreateBookingResponseDto createBooking(CreateBookingDto booking);
    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId);
}
