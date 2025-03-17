package dev.babal.ordersservice.order.web;

import dev.babal.ordersservice.order.domain.Order;
import dev.babal.ordersservice.order.domain.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public Flux<Order> getOrders(@AuthenticationPrincipal Jwt jwt) {
        return orderService.findAllByCreatedBy(jwt.getSubject());
    }

    @PostMapping
    public Mono<Order> submitOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.submitOrder(
            orderRequest.isbn(),
            orderRequest.quantity()
        );
    }
}
