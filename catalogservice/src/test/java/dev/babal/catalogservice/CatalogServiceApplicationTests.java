package dev.babal.catalogservice;

import dev.babal.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookIsCreated() {
        var expectedBook = Book.of("1234567890", "Title", "Author", 9.90, null);
        webTestClient.post()
            .uri("/books")
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book.class).value(actualBook -> {
                assertNotNull(actualBook);
                assertEquals(actualBook.title(), expectedBook.title());
                assertEquals(actualBook.author(), expectedBook.author());
                assertEquals(actualBook.isbn(), expectedBook.isbn());
                assertEquals(actualBook.price(), expectedBook.price());
            });
    }
}
