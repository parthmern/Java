package org.example.uberclientsocketservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestRequestDto {
    public String data;

    @Override
    public String toString(){
        return this.data;
    }
}
