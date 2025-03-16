package dev.babal.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.*;

import java.time.Instant;

public record Book(

    @Id
    Long id,

    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.")
    String isbn,

    @NotBlank(message = "Title is mandatory")
    String title,

    @NotBlank(message = "Author is mandatory")
    String author,

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    Double price,

    String publisher,

    @CreatedDate
    Instant createdDate,

    @CreatedBy
    String createdBy,

    @LastModifiedBy
    String lastModifiedBy,

    @LastModifiedDate
    Instant lastModifiedDate,

    @Version
    int version

) {
    public static Book of(String isbn, String title, String author, Double price, String publisher) {
        return new Book(null, isbn, title, author, price, publisher, null, null, null, null, 0);
    }
}
