package dev.babal.catalogservice.demo;

import dev.babal.catalogservice.domain.Book;
import dev.babal.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata")
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void loadData() {
        bookRepository.deleteAll();
        bookRepository.deleteAll();
        var books = List.of(Book.of("9783161484", "The Great Gatsby", "F. Scott Fitzgerald", 1925D),
            Book.of("9781566199", "To Kill a Mockingbird", "Harper Lee", 1960D),
            Book.of("9780743273", "1984", "George Orwell", 1949D),
            Book.of("9780452284", "Pride and Prejudice", "Jane Austen", 1813D));
        bookRepository.saveAll(books);
    }
}
