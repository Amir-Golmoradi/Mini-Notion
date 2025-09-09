package dev.amirgol.springtaskbackend;

import dev.amirgol.springtaskbackend.attachment.domain.model.Attachment;
import dev.amirgol.springtaskbackend.attachment.infrastructure.config.MinioProperties;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.modulith.Modulith;

@Slf4j
@Modulith
@SpringBootApplication
@EnableConfigurationProperties(MinioProperties.class)
public class App {
    public static void main(String[] args) {
        log.info("Starting Application ... ");
        SpringApplication.run(App.class, args);
    }
}
