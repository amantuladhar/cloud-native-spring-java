package dev.babal.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "polar")
public record PolarProperties(String greeting) {

}
