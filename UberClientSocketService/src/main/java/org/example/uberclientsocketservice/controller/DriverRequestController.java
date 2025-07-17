package org.example.uberclientsocketservice.controller;

import org.example.uberclientsocketservice.dtos.RideRequestDto;
import org.example.uberclientsocketservice.dtos.RideResponseDto;
import org.example.uberclientsocketservice.dtos.TestRequestDto;
import org.example.uberclientsocketservice.dtos.TestResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/socket")
public class DriverRequestController {

    private SimpMessagingTemplate messagingTemplate;

    public DriverRequestController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
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
        System.out.println("raiseRideRequest- Execute periodic task");
        // TODO: req only goes to nearby drivers - just room / userid
        messagingTemplate.convertAndSend("/topic/rideRequest", requestDto);
    }

    @MessageMapping("/rideResponse")
    public void rideRequestHandler(RideResponseDto rideResponseDto){
        System.out.println(rideResponseDto.response +  "by driver" + rideResponseDto.driverId);
    }




}
