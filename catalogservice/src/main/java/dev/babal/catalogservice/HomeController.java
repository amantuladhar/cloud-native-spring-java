package dev.babal.catalogservice;

import dev.babal.catalogservice.config.CNativeProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final CNativeProperties properties;

    public HomeController(CNativeProperties properties) {
        this.properties = properties;
    }

    @GetMapping
    public String getGreeting() {
        return properties.getGreeting();
    }
}
