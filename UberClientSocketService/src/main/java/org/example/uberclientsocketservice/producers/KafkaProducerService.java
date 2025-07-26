package org.example.uberclientsocketservice.producers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.uberclientsocketservice.dtos.BookingUpdateMessage;
import org.example.uberclientsocketservice.dtos.UpdateBookingRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Message sent: " + message);
    }

    public void sendBookingUpdate(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId) {
        try {
            updateBookingRequestDto.setBookingId(bookingId);
            BookingUpdateMessage message = BookingUpdateMessage.builder()
                    .bookingId(bookingId)
                    .update(updateBookingRequestDto)
                    .build();

            String jsonMessage = new ObjectMapper().writeValueAsString(message);

            kafkaTemplate.send("booking-updates", jsonMessage);
            System.out.println("💛 (booking-updates) Produced ---- Booking update produced to Kafka topic ==> " + jsonMessage);

        } catch (Exception e) {
            throw new RuntimeException("❌ --- ERROR to serialize Booking Update DTO", e);
        }
    }


}
