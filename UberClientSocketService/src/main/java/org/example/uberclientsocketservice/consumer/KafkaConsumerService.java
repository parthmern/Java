package org.example.uberclientsocketservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.uberclientsocketservice.controller.DriverRequestController;
import org.example.uberclientsocketservice.dtos.RideRequestDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final ObjectMapper objectMapper;
    private final DriverRequestController driverRequestController;


    public KafkaConsumerService(ObjectMapper objectMapper, DriverRequestController driverRequestController) {
        this.objectMapper = objectMapper;
        this.driverRequestController = driverRequestController;
    }

    @KafkaListener(topics = "sample-topic")
    public void consume(String message) {
        System.out.println("Message received from sample-topic: " + message);
    }

//    @KafkaListener(topics = "ride-requests", groupId = "socket-service-group")
//    public void consumeRideRequests(String message) {
//        System.out.println("💚 ---Ride Request message rcv ==> " + message);
//    }

    @KafkaListener(topics = "ride-requests", groupId = "socket-service-group")
    public void consumeRideRequests(String message) {
        try {
            RideRequestDto requestDto = objectMapper.readValue(message, RideRequestDto.class);

            System.out.println("💚 --- Ride Request rcvd ==>" + requestDto);

            driverRequestController.sendDriversNewRequest(requestDto);

        } catch (Exception e) {
            System.err.println("❌ error to deserialize RideRequestDto ERROR =>" + e.getMessage());
        }
    }



}
