package dev.babal.dispatcherservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@FunctionalSpringBootTest
class DispatchingFunctionsTests {
    @Autowired
    private FunctionCatalog catalog;

    @Test
    void packAndLabelOrder() {
        Function<OrderAcceptMessage, Flux<OrderDispatchedMessage>> fn = catalog.lookup(Function.class, "pack|label");
        long orderId = 121;

        StepVerifier.create(fn.apply(new OrderAcceptMessage(orderId)))
            .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
            .verifyComplete();
    }

    @Test
    void packOrder() {
        Function<OrderAcceptMessage, Long> fn = catalog.lookup(Function.class, "pack");
        long orderId = 121;

        StepVerifier.create(Flux.just(fn.apply(new OrderAcceptMessage(orderId))))
            .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(orderId))
            .verifyComplete();
    }

    @Test
    void labelOrder() {
        Function<Flux<Long>, Flux<OrderDispatchedMessage>> fn = catalog.lookup(Function.class, "label");
        long orderId = 121;

        StepVerifier.create(fn.apply(Flux.just(orderId)))
            .expectNextMatches(dispatchedOrder -> dispatchedOrder.equals(new OrderDispatchedMessage(orderId)))
            .verifyComplete();
    }
}
