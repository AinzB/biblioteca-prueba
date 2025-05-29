-- V7__create_book_loans_table.sql
CREATE TABLE book_loans (
  id            NUMBER PRIMARY KEY,
  book_copy_id  NUMBER NOT NULL,
  student_id    NUMBER NOT NULL,
  loan_date     DATE DEFAULT SYSDATE NOT NULL,
  due_date      DATE NOT NULL,
  return_date   DATE,
  CONSTRAINT fk_loans_copy    FOREIGN KEY (book_copy_id) REFERENCES book_copies(id),
  CONSTRAINT fk_loans_student FOREIGN KEY (student_id)   REFERENCES students(id) --
);
