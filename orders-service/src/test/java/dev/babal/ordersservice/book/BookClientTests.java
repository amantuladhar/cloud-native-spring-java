package dev.babal.ordersservice.book;

import lombok.SneakyThrows;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@TestMethodOrder(MethodOrderer.Random.class)
class BookClientTests {
    private MockWebServer mockWebServer;
    private BookClient bookClient;


    @BeforeEach
    @SneakyThrows
    void setup() {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
        var webClient = WebClient.builder()
            .baseUrl(mockWebServer.url("/").uri().toString())
            .build();
        this.bookClient = new BookClient(webClient);
    }

    @AfterEach
    @SneakyThrows
    void clean() {
        this.mockWebServer.shutdown();
    }

    @Test
    void whenBookExistsThenReturnBook() {
        var bookIsbn = "1234567890";

        var mockResponse = new MockResponse()
            .addHeader("Content-Type", "application/json")
            .setBody("""
                	{
                		"isbn": %s,
                		"title": "Title",
                		"author": "Author",
                		"price": 9.90,
                		"publisher": "Test Publisher"
                	}
                """.formatted(bookIsbn));

        mockWebServer.enqueue(mockResponse);

        Mono<Book> book = bookClient.getBookByIsbn(bookIsbn);

        StepVerifier.create(book)
            .expectNextMatches(b -> b.isbn().equals(bookIsbn))
            .verifyComplete();
    }

    @Test
    void whenBookNotExistsThenReturnEmpty() {
        var isbn = "1234567890";

        var mockResponse = new MockResponse()
            .addHeader("Content-Type", "application/json")
            .setResponseCode(404);

        mockWebServer.enqueue(mockResponse);

        var book = bookClient.getBookByIsbn(isbn);
        StepVerifier.create(book)
            .expectNextCount(0)
            .verifyComplete();
    }
}
