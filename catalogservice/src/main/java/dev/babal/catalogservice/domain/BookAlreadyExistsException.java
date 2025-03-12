package dev.babal.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn, Throwable cause) {
        super("Book with ISBN " + isbn + " already exists.", cause);
    }

    public BookAlreadyExistsException(String isbn) {
        this(isbn, null);
    }
}
