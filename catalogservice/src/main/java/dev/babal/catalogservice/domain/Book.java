package dev.babal.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(
        @NotBlank(message = "ISBN is mandatory") @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.") String isbn,

        @NotBlank(message = "Title is mandatory") String title,

        @NotBlank(message = "Author is mandatory") String author,
        @NotNull(message = "Price is mandatory") @Positive(message = "Price must be positive") Double price) {

}
