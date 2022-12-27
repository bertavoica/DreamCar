package com.example.dreamcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DreamCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamCarApplication.class, args);
    }

}
