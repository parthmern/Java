package org.example.uberbookingservice.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.uberbookingservice.dtos.RideRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Message sent: " + message);
    }

    public void publishRideRequest(String topic, RideRequestDto rideRequestDto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(rideRequestDto);
            kafkaTemplate.send(topic, jsonMessage);
            System.out.println("💚 ----- RideRequest sent to Kafka ==> " + jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize RideRequestDto", e);
        }
    }


}
