package dev.babal.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn, Throwable cause) {
        super("Book with ISBN " + isbn + " not found.", cause);
    }

    public BookNotFoundException(String isbn) {
        this(isbn, null);
    }

}
