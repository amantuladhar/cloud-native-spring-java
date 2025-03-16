package dev.babal.ordersservice.order.web;

import dev.babal.ordersservice.config.SecurityConfig;
import dev.babal.ordersservice.order.domain.Order;
import dev.babal.ordersservice.order.domain.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@WebFluxTest(OrderController.class)
@Import(SecurityConfig.class)
class OrderControllerTests {

    @Autowired
    private WebTestClient webClient;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ReactiveJwtDecoder reactiveJwtDecoder;

    @Test
    void whenBookNotAvailableThenRejectOrder() {
        var orderRequest = new OrderRequest("1231231231", 1);
        var expectedOrder = OrderService.buildRejectedOrder(orderRequest.isbn(), orderRequest.quantity());
        given(orderService.submitOrder("1231231231", 1))
            .willReturn(Mono.just(expectedOrder));


        webClient
            .mutateWith(SecurityMockServerConfigurers.mockJwt()
                .authorities(new SimpleGrantedAuthority("ROLE_employee")))
            .post()
            .uri("/orders")
            .bodyValue(orderRequest)
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(Order.class)
            .value(actualOrder -> {
                assertEquals(expectedOrder.bookIsbn(), actualOrder.bookIsbn());
                assertEquals(expectedOrder.status(), actualOrder.status());
            });
    }
}
