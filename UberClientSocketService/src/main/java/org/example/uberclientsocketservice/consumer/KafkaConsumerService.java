package org.example.uberclientsocketservice.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topics = "sample-topic")
    public void consume(String message) {
        System.out.println("Message received from sample-topic: " + message);
    }

}
