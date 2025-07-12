package org.example.uberclientsocketservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private String name;
    private String message;
    private String timeStamp;
}
