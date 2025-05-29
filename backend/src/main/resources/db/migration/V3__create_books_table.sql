-- V3__create_books_table.sql
CREATE TABLE books (
  id               NUMBER PRIMARY KEY,
  title            VARCHAR2(200)  NOT NULL,
  isbn             VARCHAR2(20)   UNIQUE NOT NULL,
  publication_year NUMBER,
  category         VARCHAR2(50),
  image_url        VARCHAR2(200),
  publisher_id     NUMBER         NOT NULL,
  author_id        NUMBER         NOT NULL,
  created_at       TIMESTAMP      DEFAULT SYSTIMESTAMP,
  CONSTRAINT fk_books_publisher FOREIGN KEY (publisher_id) REFERENCES publishers(id),
  CONSTRAINT fk_books_author FOREIGN KEY (author_id) REFERENCES authors(id)
);
