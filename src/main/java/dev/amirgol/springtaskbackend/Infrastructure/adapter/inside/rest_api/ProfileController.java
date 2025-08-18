package dev.amirgol.springtaskbackend.Infrastructure.adapter.inside.rest_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/profile")
public class ProfileController {
    @Value("${spring.datasource.url}")
    private String dbUrl;


    @GetMapping
    public Map<String, String> getProfileInfo() {
        return Map.of(
                "databaseUrl", dbUrl
        );
    }
}
