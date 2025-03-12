package dev.babal.catalogservice;


import dev.babal.catalogservice.config.DataConfig;
import dev.babal.catalogservice.domain.Book;
import dev.babal.catalogservice.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    void findBookByIsbnWhenBookExists() {
        var isbn = "1234561239";
        var book = Book.of(isbn, "The Great Gatsby", "F. Scott Fitzgerald", 1925D, null);
        jdbcAggregateTemplate.insert(book);
        bookRepository.findByIsbn(isbn)
            .ifPresentOrElse(
                foundBook -> {
                    assert foundBook.isbn().equals(isbn);
                    assert foundBook.title().equals("The Great Gatsby");
                    assert foundBook.author().equals("F. Scott Fitzgerald");
                    assert foundBook.price().equals(1925D);
                },
                () -> {
                    throw new AssertionError("Book not found");
                }
            );
    }

    @Test
    void findAllBooks() {
        var book1 = Book.of("1234561235", "Title", "Author", 12.90, null);
        var book2 = Book.of("1234561236", "Another Title", "Author", 12.90, null);
        jdbcAggregateTemplate.insert(book1);
        jdbcAggregateTemplate.insert(book2);

        Iterable<Book> actualBooks = bookRepository.findAll();

        assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
            .filter(book -> book.isbn().equals(book1.isbn()) || book.isbn().equals(book2.isbn()))
            .collect(Collectors.toList())).hasSize(2);
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        Optional<Book> actualBook = bookRepository.findByIsbn("1234561238");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void existsByIsbnWhenExisting() {
        var bookIsbn = "1234561239";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, null);
        jdbcAggregateTemplate.insert(bookToCreate);

        boolean existing = bookRepository.existsByIsbn(bookIsbn);

        assertThat(existing).isTrue();
    }

    @Test
    void existsByIsbnWhenNotExisting() {
        boolean existing = bookRepository.existsByIsbn("1234561240");
        assertThat(existing).isFalse();
    }

    @Test
    void deleteByIsbn() {
        var bookIsbn = "1234561241";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90, null);
        var persistedBook = jdbcAggregateTemplate.insert(bookToCreate);

        bookRepository.deleteByIsbn(bookIsbn);

        assertThat(jdbcAggregateTemplate.findById(persistedBook.id(), Book.class)).isNull();
    }
}
