package com.app.neueda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NeuedaShortUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeuedaShortUrlApplication.class, args);
    }

}
