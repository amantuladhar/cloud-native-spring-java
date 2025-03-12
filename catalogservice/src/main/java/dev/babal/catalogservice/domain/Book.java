package dev.babal.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

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

    @CreatedDate
    Instant createdDate,

    @LastModifiedDate
    Instant lastModifiedDate,

    @Version
    int version

) {
    public static Book of(String isbn, String title, String author, Double price) {
        return new Book(null, isbn, title, author, price, null, null, 0);
    }
}
