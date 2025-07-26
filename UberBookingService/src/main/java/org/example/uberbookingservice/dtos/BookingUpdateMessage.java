package org.example.uberbookingservice.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingUpdateMessage {
    private Long bookingId;
    private UpdateBookingRequestDto update;

}
