package org.example.uberentityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UberEntityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberEntityServiceApplication.class, args);
	}

}
