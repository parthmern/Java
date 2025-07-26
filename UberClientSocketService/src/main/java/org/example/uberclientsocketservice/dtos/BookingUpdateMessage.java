package org.example.uberclientsocketservice.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingUpdateMessage {
    private Long bookingId;
    private UpdateBookingRequestDto update;

    public Builder update(UpdateBookingRequestDto update) {
        this.update = update;
        return (Builder) this;
    }

    public Builder addBookingIdInUpdate(Long bookingId) {
        this.update.setBookingId(bookingId);
        return (Builder) this;
    }


}
