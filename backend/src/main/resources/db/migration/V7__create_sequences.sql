-- V8__create_sequences.sql
-- Secuencias para Author, Publisher, Book, BookAuthor, Student, BookCopy, BookLoan

CREATE SEQUENCE PUBLISHER_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE SEQUENCE AUTHOR_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE SEQUENCE BOOK_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;
  
CREATE SEQUENCE STUDENT_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE SEQUENCE BOOK_COPY_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE SEQUENCE BOOK_LOAN_SEQ
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;
-- 