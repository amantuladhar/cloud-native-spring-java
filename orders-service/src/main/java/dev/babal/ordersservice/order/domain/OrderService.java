package dev.babal.ordersservice.order.domain;

import dev.babal.ordersservice.book.Book;
import dev.babal.ordersservice.book.BookClient;
import dev.babal.ordersservice.order.event.OrderAcceptedMessage;
import dev.babal.ordersservice.order.event.OrderDispatchedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;
    private final StreamBridge streamBridge;

    public static Order buildRejectedOrder(String isbn, int quantity) {
        return Order.of(isbn, null, null, quantity, OrderStatus.REJECTED);
    }

    public static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.of(book.isbn(), book.title() + " - " + book.author(), book.price(), quantity, OrderStatus.ACCEPTED);
    }

    public Order buildDispatchedOrder(Order order) {
        return new Order(
            order.id(),
            order.bookIsbn(),
            order.bookName(),
            order.bookPrice(),
            order.quantity(),
            OrderStatus.DISPATCHED,
            order.createdDate(),
            order.lastModifiedDate(),
            order.version()
        );
    }

    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }


    @Transactional
    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn)
            .map(book -> buildAcceptedOrder(book, quantity))
            .defaultIfEmpty(buildRejectedOrder(isbn, quantity))
            .flatMap(orderRepository::save)
            .doOnNext(this::publishOrderAcceptedEvent);
    }

    public Flux<Order> consumeOrderDispatchedMessage(Flux<OrderDispatchedMessage> flux) {
        return flux.flatMap(msg -> orderRepository.findById(msg.orderId()))
            .map(this::buildDispatchedOrder)
            .flatMap(orderRepository::save);
    }

    private void publishOrderAcceptedEvent(Order order) {
        if (order.status() != OrderStatus.ACCEPTED) {
            return;
        }
        var orderAcceptedMessage = new OrderAcceptedMessage(order.id());
        log.info("Sending order accepted message: {}", orderAcceptedMessage);
        var result = streamBridge.send("acceptOrder-out-0", orderAcceptedMessage);
        log.info("Order accepted message sent: OrderId({}) {}", order.id(), result);
    }
}
