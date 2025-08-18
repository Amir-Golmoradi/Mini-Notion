package dev.amirgol.springtaskbackend.Infrastructure.adapter.inside.rest_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/env")
public class EnvController {

    @Value("${DEV_DB_HOST}")
    private String devDbHost;

    @GetMapping
    public String getDevDbHost() {
        return "Dev Db Host: " + devDbHost;
    }
}
