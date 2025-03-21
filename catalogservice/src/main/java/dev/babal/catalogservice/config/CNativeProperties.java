package dev.babal.catalogservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cnative") // @RefreshScope // Already listens to RefreshScopeRefreshedEvent
@Getter
@Setter
public class CNativeProperties {
    /**
     * A message to welcome users.
     */
    private String greeting;
}
