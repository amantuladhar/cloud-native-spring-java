package dev.babal.ordersservice.order.event;

import dev.babal.ordersservice.order.domain.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class OrderFunctions {

    @Bean
    public Consumer<Flux<OrderDispatchedMessage>> orderDispatched(OrderService orderService) {
        return messageFlux -> {
            orderService.consumeOrderDispatchedMessage(messageFlux)
                .doOnNext(orderDispatchedMessage -> {
                    log.info("Order dispatched message consumed: {}", orderDispatchedMessage);
                })
                .subscribe();

        };
    }
}
