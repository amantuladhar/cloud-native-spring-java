package dev.babal.ordersservice.order.web;

import dev.babal.ordersservice.config.ClientProperties;
import dev.babal.ordersservice.order.domain.Order;
import dev.babal.ordersservice.order.domain.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final ClientProperties clientProperties;

    @GetMapping
    public Flux<Order> getOrders() {
        return orderService.findAll();
    }

    @PostMapping
    public Mono<Order> submitOrder(
        @Valid @RequestBody OrderRequest orderRequest
    ) {
        log.info("Submitting order for ISBN: {}, Quantity: {}, catalog: {}",
            orderRequest.isbn(), orderRequest.quantity(),
            clientProperties.getCatalogServiceUri().toString()
        );
        return orderService.submitOrder(
            orderRequest.isbn(),
            orderRequest.quantity()
        );
    }
}
