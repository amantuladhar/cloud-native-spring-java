package dev.babal.catalogservice.demo;

import dev.babal.catalogservice.domain.Book;
import dev.babal.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
public class BookDataLoader {
    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void loadData() {
        bookRepository.save(new Book("9783161484", "The Great Gatsby", "F. Scott Fitzgerald", 1925D));
        bookRepository.save(new Book("9781566199", "To Kill a Mockingbird", "Harper Lee", 1960D));
        bookRepository.save(new Book("9780743273", "1984", "George Orwell", 1949D));
        bookRepository.save(new Book("9780452284", "Pride and Prejudice", "Jane Austen", 1813D));
    }
}
