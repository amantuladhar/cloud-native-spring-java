package dev.babal.edge_service.user;

import dev.babal.edge_service.config.SecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@WebFluxTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTests {

    @Autowired
    WebTestClient webTestClient;

    @MockitoBean
    ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Test
    void whenNotAuthenticatedThenUnauthorized() {
        webTestClient
            .get()
            .uri("/user")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    void whenAuthenticatedThenOk() {
        var expectedUser = new User("test", "Test", "User", List.of("ROLE_USER"));

        webTestClient
            .mutateWith(configureMockOidcLogin(expectedUser))
            .get()
            .uri("/user")
            .exchange()
            .expectStatus().is2xxSuccessful()
            .expectBody(User.class)
            .value(user -> {
                Assertions.assertEquals(expectedUser.firstName(), user.firstName());
                Assertions.assertEquals(expectedUser.lastname(), user.lastname());
                Assertions.assertEquals(expectedUser.username(), user.username());
            });

    }

    private SecurityMockServerConfigurers.OidcLoginMutator configureMockOidcLogin(User user) {
        return SecurityMockServerConfigurers.mockOidcLogin().idToken(builder -> {
            builder.claim(StandardClaimNames.PREFERRED_USERNAME, user.username());
            builder.claim(StandardClaimNames.GIVEN_NAME, user.firstName());
            builder.claim(StandardClaimNames.FAMILY_NAME, user.lastname());
        });
    }
}
