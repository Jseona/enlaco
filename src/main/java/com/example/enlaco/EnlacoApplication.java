package com.example.enlaco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EnlacoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnlacoApplication.class, args);
    }

}
