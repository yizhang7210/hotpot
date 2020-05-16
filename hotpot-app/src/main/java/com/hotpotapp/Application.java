package com.hotpotapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        try {
            new SpringApplication(Application.class).run(args);
        } catch (Exception e) {
            log.error("Service shutting down because of exception.", e);
        }
    }
}
