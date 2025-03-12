package dev.babal.catalogservice;

import dev.babal.catalogservice.domain.BookNotFoundException;
import dev.babal.catalogservice.domain.BookService;
import dev.babal.catalogservice.web.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void testGetBookNotExistingThenShouldThrowException() throws Exception {
        String isbn = "1234567890";
        given(bookService.viewBookDetails(isbn)).willThrow(new BookNotFoundException(isbn));
        mockMvc.perform(get("/books/{isbn}", isbn)).andExpect(status().isNotFound());
    }
}
