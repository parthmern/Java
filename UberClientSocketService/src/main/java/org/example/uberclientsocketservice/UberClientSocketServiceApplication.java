package org.example.uberclientsocketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UberClientSocketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UberClientSocketServiceApplication.class, args);
    }

}
