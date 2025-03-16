package dev.babal.catalogservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import dev.babal.catalogservice.domain.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
@Testcontainers
class CatalogServiceApplicationTests {

    @Container
    private static final KeycloakContainer keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:19.0")
        .withRealmImportFile("test-realm-config.json");
    private static KeycloakToken user1Token;
    private static KeycloakToken user2Token;
    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloakContainer.getAuthServerUrl() + "/realms/cnative-test");
        registry.add("logging.level.org.springframework.security", () -> "DEBUG");
    }

    @BeforeAll
    static void generateAccessToken() {
        WebClient webClient = WebClient.builder()
            .baseUrl(keycloakContainer.getAuthServerUrl() + "/realms/cnative-test/protocol/openid-connect/token")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();

        user1Token = authenticateWith("user1", "password", webClient);
        user2Token = authenticateWith("user2", "password", webClient);
    }

    private static KeycloakToken authenticateWith(String username, String password, WebClient webClient) {
        return webClient.post()
            // test-realm is configured to accept password grant type instead of authorization grant flow
            .body(BodyInserters.fromFormData("grant_type", "password")
                .with("client_id", "cnative-test")
                .with("username", username)
                .with("password", password)
            ).retrieve()
            .bodyToMono(KeycloakToken.class)
            .block();
    }

    @Test
    void whenPostRequestThenBookIsCreated() {
        var expectedBook = Book.of("1234567890", "Title", "Author", 9.90, null);
        webTestClient.post()
            .uri("/books")
            .headers(header -> header.setBearerAuth(user1Token.accessToken()))
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

    @Test
    void whenPostRequestUnauthorizedThen403() {
        var expectedBook = Book.of("1231231231", "Title", "Author",
            9.90, "Polarsophia");

        webTestClient.post().uri("/books")
            .headers(headers ->
                headers.setBearerAuth(user2Token.accessToken()))
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isForbidden();
    }

    @Test
    void whenPostRequestUnauthenticatedThen401() {
        var expectedBook = Book.of("1231231231", "Title", "Author",
            9.90, "Polarsophia");

        webTestClient.post().uri("/books")
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isUnauthorized();
    }

    private record KeycloakToken(String accessToken) {
        @JsonCreator
        private KeycloakToken(@JsonProperty("access_token") final String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
