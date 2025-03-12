package dev.babal.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "polar") // Already listens to RefreshScopeRefreshedEvent
public record PolarProperties(String greeting) {

}
