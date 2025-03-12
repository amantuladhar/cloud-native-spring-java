package dev.babal.catalogservice.domain;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBookRepository implements BookRepository {
    private static final Map<String, Book> bookStore = new ConcurrentHashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return bookStore.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return existsByIsbn(isbn) ? Optional.of(bookStore.get(isbn)) : Optional.empty();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return bookStore.get(isbn) != null;
    }

    @Override
    public Book save(Book book) {
        bookStore.put(book.isbn(), book);
        return book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookStore.remove(isbn);
    }
}
