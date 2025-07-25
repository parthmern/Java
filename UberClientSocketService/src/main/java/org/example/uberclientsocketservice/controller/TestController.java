package org.example.uberclientsocketservice.controller;

import lombok.*;
import org.example.uberclientsocketservice.dtos.ChatRequest;
import org.example.uberclientsocketservice.dtos.ChatResponse;
import org.example.uberclientsocketservice.dtos.TestRequestDto;
import org.example.uberclientsocketservice.dtos.TestResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/ping")    // client can send req to server on /ping
    @SendTo("/topic/ping")      // server can send req to client /topic/ping
    public TestResponseDto pingCheck(TestRequestDto testRequestDto){
        System.out.println("received /app/ping from client => " +  testRequestDto );
        return new TestResponseDto("Received" + testRequestDto + "on serverside");
    }

    // emit direct to the client
//    @Scheduled(fixedDelay = 2000)    // executing this function every 2s
//    public void sendPeriodicMessage() {
//        String msg = "Fixed delay task - " + System.currentTimeMillis();
//        System.out.println(msg);
//        messagingTemplate.convertAndSend("/topic/scheduled", msg);
//    }

    @MessageMapping("/chat/{room}")
    @SendTo("/topic/message/{room}")
    public ChatResponse chatMessage(@DestinationVariable String room, ChatRequest request){
        ChatResponse response = ChatResponse.builder()
                .name(request.getName())
                .message(request.getMessage())
                .timeStamp(""+System.currentTimeMillis())
                .build();
        return response;
    }

    @MessageMapping("/privateChat/{room}/{userId}")
//    @SendTo("/topic/privateMessage/{room}/{userId}")
    public void privateChatMessage(@DestinationVariable String room, @DestinationVariable String userId, ChatRequest request){
        ChatResponse response = ChatResponse.builder()
                .name(request.getName())
                .message(request.getMessage())
                .timeStamp(""+System.currentTimeMillis())
                .build();
        messagingTemplate.convertAndSendToUser(userId, "/queue/privateMessage/" + room, response);

    }



}
