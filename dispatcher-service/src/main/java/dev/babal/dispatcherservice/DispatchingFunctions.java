package dev.babal.dispatcherservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Slf4j
@Configuration
public class DispatchingFunctions {

    @Bean
    public Function<OrderAcceptMessage, Long> pack() {
        return orderAcceptMessage -> {
            log.info("Packing order {}", orderAcceptMessage.orderId());
            return orderAcceptMessage.orderId();
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(orderId -> {
            log.info("Labeling order {}", orderId);
            return new OrderDispatchedMessage(orderId);
        });
    }

}
