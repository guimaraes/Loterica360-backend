package com.loteria360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Loteria360Application {

    public static void main(String[] args) {
        SpringApplication.run(Loteria360Application.class, args);
    }
}
