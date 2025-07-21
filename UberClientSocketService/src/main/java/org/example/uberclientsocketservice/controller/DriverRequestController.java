package org.example.uberclientsocketservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.example.uberclientsocketservice.dtos.RideRequestDto;
import org.example.uberclientsocketservice.dtos.RideResponseDto;
import org.example.uberclientsocketservice.dtos.UpdateBookingRequestDto;
import org.example.uberclientsocketservice.dtos.UpdateBookingResponseDto;
import org.example.uberclientsocketservice.producers.KafkaProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/api/socket")
public class DriverRequestController {


    private final KafkaProducerService kafkaProducerService;
    private ObjectMapper objectMapper;
    private SimpMessagingTemplate messagingTemplate;
    private RestTemplate restTemplate;

    public DriverRequestController(SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper, KafkaProducerService kafkaProducerService) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
        this.restTemplate = new RestTemplate();
        this.kafkaProducerService = kafkaProducerService;
    }

//    @Scheduled  (fixedDelay = 2000)    // executing this function every 2s
//    public void sendPeriodicMessage() {
//        String msg = "Fixed delay task - " + System.currentTimeMillis();
//        System.out.println(msg);
//        messagingTemplate.convertAndSend("/topic/scheduled", msg);
//    }

    @PostMapping("/newride")
    public ResponseEntity<Boolean> raiseRideRequest(@RequestBody RideRequestDto requestDto) {
        sendDriversNewRequest(requestDto);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public void sendDriversNewRequest(RideRequestDto requestDto) {
        try {
            String jsonString = objectMapper.writeValueAsString(requestDto);
            System.out.println("/newride raiseRideRequest - JSON: " + jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: req only goes to nearby drivers - just room / userid
        messagingTemplate.convertAndSend("/topic/rideRequest", requestDto);
    }

    // synchronized means -> sync function ( concurrent DS handle )
    // + handle concurrency on DB side ( watch atomicity , isolation level on DB )

    @MessageMapping("/rideResponse/{userId}")
    public synchronized void rideRequestHandler(@DestinationVariable String userId, RideResponseDto rideResponseDto){
        System.out.println(rideResponseDto.response +  " <- by driverId : " + userId + " and booking id:" + rideResponseDto.bookingId);

        UpdateBookingRequestDto updateBookingRequestDto = UpdateBookingRequestDto.builder()
                .driverId(Optional.of(Long.parseLong(userId)))
                .bookingStatus("SCHEDULED")
                .build();
        ResponseEntity<UpdateBookingResponseDto> result = this.restTemplate.postForEntity("http://localhost:8000/api/v1/booking/"+ rideResponseDto.bookingId, updateBookingRequestDto, UpdateBookingResponseDto.class);
        System.out.println(result.getStatusCode()+ " "+ result.getBody() );
        kafkaProducerService.publishMessage("sample-topic", "hello");   // ANOTHER WAY
    }

    @GetMapping
    public ResponseEntity<Boolean> doSomething() {
        kafkaProducerService.publishMessage("sample-topic", "hello");
        return ResponseEntity.ok(true);
    }


}
