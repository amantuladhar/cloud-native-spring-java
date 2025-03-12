DROP TABLE if EXISTS book;

CREATE TABLE book
(
    id                 BIGSERIAL      NOT NULL,
    author             VARCHAR(255)   NOT NULL,
    isbn               VARCHAR(255)   NOT NULL,
    price              DECIMAL(10, 2) NOT NULL,
    title              VARCHAR(255)   NOT NULL,
    created_date       TIMESTAMP      NOT NULL,
    last_modified_date TIMESTAMP      NOT NULL,
    version            INTEGER        NOT NULL,
    CONSTRAINT book_pk_id PRIMARY KEY (id),
    CONSTRAINT book_uq_isbn UNIQUE (isbn)
);
