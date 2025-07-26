package org.example.uberbookingservice.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.uberbookingservice.dtos.BookingUpdateMessage;
import org.example.uberbookingservice.dtos.RideRequestDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaConsumerService {
    private final ObjectMapper objectMapper;

    public KafkaConsumerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sample-topic")
    public void consume(String message) {
        System.out.println("Message received from sample-topic: " + message);
    }

    @KafkaListener(topics = "booking-updates")
    public void consumeBookingUpdates(String message) {
        try {
            BookingUpdateMessage bookingUpdate = objectMapper.readValue(message, BookingUpdateMessage.class);
            System.out.println("💛 (booking-updates) consumed");
            System.out.println("Booking ID- " + bookingUpdate.getBookingId());
            System.out.println("Update DTO- " + bookingUpdate.getUpdate().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
