-- V5__create_students_table.sql
CREATE TABLE students (
  id            NUMBER PRIMARY KEY,
  student_code  VARCHAR2(20)  UNIQUE NOT NULL,
  first_name    VARCHAR2(100) NOT NULL,
  last_name     VARCHAR2(100) NOT NULL,
  email         VARCHAR2(150) UNIQUE NOT NULL,
  phone         VARCHAR2(20),
  status        VARCHAR2(10)  DEFAULT 'ACTIVE' NOT NULL, --
  created_at    TIMESTAMP     DEFAULT SYSTIMESTAMP
);
