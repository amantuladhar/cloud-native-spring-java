package dev.babal.edge_service.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
@Import(SecurityConfig.class)
class SecurityConfigTests {

    @Autowired
    WebTestClient webTestClient;

    @MockitoBean
    ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Test
    void whenLogoutAuthenticatedAndWithCsrfTokenThen302() {
        Mockito.when(clientRegistrationRepository.findByRegistrationId("test"))
            .thenReturn(Mono.just(testClientRegistration()));
        webTestClient
            .mutateWith(SecurityMockServerConfigurers.mockOidcLogin())
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post()
            .uri("/logout")
            .exchange()
            .expectStatus()
            .isFound();
    }

    @Test
    void whenLogoutNotAuthenticatedAndNoCsrfTokenThen403() {
        webTestClient
            .post()
            .uri("/logout")
            .exchange()
            .expectStatus().isForbidden();
    }

    @Test
    void whenLogoutAuthenticatedAndNoCsrfTokenThen403() {
        webTestClient
            .mutateWith(SecurityMockServerConfigurers.mockOidcLogin())
            .post()
            .uri("/logout")
            .exchange()
            .expectStatus().isForbidden();
    }

    private ClientRegistration testClientRegistration() {
        return ClientRegistration.withRegistrationId("test")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .clientId("test")
            .authorizationUri("https://sso.example.com/auth")
            .tokenUri("https://sso.example.com/token")
            .redirectUri("https://example.com")
            .build();
    }
}
