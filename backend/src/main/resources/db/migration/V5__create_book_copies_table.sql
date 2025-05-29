-- V6__create_book_copies_table.sql
CREATE TABLE book_copies (
  id            NUMBER PRIMARY KEY,
  book_id       NUMBER NOT NULL,
  publisher_id  NUMBER NOT NULL,
  status        VARCHAR2(20) DEFAULT 'DISPONIBLE' NOT NULL, -- DISPONIBLE | PRESTADO | PERDIDO
  condition     VARCHAR2(10) DEFAULT 'BUENA' NOT NULL,  -- EXCELENTE | BUENA | MALA
  created_at    TIMESTAMP DEFAULT SYSTIMESTAMP,
  CONSTRAINT fk_copies_book      FOREIGN KEY (book_id)      REFERENCES books(id),
  CONSTRAINT fk_copies_publisher FOREIGN KEY (publisher_id) REFERENCES publishers(id) --
);
