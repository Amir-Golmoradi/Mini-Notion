package dev.amirgol.springtaskbackend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class App {
    public static void main(String[] args) {

        log.info("Starting Application ... ");
        SpringApplication.run(App.class, args);
    }
}
