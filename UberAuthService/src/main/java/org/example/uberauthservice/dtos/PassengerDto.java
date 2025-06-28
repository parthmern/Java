package org.example.uberauthservice.dtos;

import lombok.*;
import org.example.uberauthservice.models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private Long id;
    private String name;
    private String email;
    private String password; // encrypted pswd
    private String phoneNumber;
    private Date createdAt;

    public static PassengerDto from(Passenger p){
        PassengerDto result = PassengerDto.builder().id(p.getId()).createdAt(p.getCreatedAt()).email(p.getEmail()).password(p.getPassword()).phoneNumber(p.getPhoneNumber()).name(p.getName()).build();
        return result;
    }
}
