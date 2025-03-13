CREATE TABLE orders
(
    id                 BIGSERIAL    NOT NULL,
    book_isbn          VARCHAR(255) NOT NULL,
    book_name          VARCHAR(255),
    book_price         DECIMAL(10, 2),
    quantity           INT          NOT NULL,
    status             VARCHAR(255) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL,
    version            INTEGER      NOT NULL,

    CONSTRAINT orders_pk_id PRIMARY KEY (id)
);
