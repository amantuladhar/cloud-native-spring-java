package dev.babal.catalogservice;

import dev.babal.catalogservice.config.SecurityConfig;
import dev.babal.catalogservice.domain.BookNotFoundException;
import dev.babal.catalogservice.domain.BookService;
import dev.babal.catalogservice.web.BookController;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@Import(SecurityConfig.class)
public class BookControllerMvcTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookService bookService;

    @MockitoBean
    JwtDecoder jwtDecoder;

    @Test
    void testGetBookNotExistingThenShouldThrowException() throws Exception {
        String isbn = "1234567890";
        given(bookService.viewBookDetails(isbn))
            .willThrow(new BookNotFoundException(isbn));
        mockMvc.perform(get("/books/{isbn}", isbn))
            .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testDeleteBookWithEmployeeRoleThenShouldReturn204() {
        var isbn = "1234567890";
        mockMvc.perform(delete("/books/" + isbn)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                    .authorities(new SimpleGrantedAuthority("ROLE_employee"))))
            .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void testDeleteBookWithCustomerRoleThenShouldReturn403() {
        var isbn = "1234567890";
        mockMvc.perform(delete("/books/" + isbn)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                    .authorities(new SimpleGrantedAuthority("ROLE_customer"))))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void testDeleteBookWithoutAuthenticationThenShouldReturn401() {
        var isbn = "1234567890";
        mockMvc.perform(delete("/books/" + isbn))
            .andExpect(status().isUnauthorized());
    }
}
