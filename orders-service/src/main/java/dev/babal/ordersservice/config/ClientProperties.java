package dev.babal.ordersservice.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.net.URI;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cnative")
@Getter
@Setter
public class ClientProperties {
    URI catalogServiceUri;
}
