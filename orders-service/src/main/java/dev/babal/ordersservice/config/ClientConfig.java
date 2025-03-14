package dev.babal.ordersservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class ClientConfig {

    // Can't refresh this when config changes
    // I wonder what we can do?
    // Same thing for datasource?
    // Do we need to restart the application?
    @Bean
    WebClient webClient(ClientProperties properties, WebClient.Builder webClientBuilder) {
        log.info("Catalog URI: {}", properties.getCatalogServiceUri());
        return webClientBuilder
            .baseUrl(properties.getCatalogServiceUri().toString())
            .build();
    }
}
