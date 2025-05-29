-- V2__create_authors_table.sql
CREATE TABLE authors (
  id           NUMBER PRIMARY KEY,
  name         VARCHAR2(150) NOT NULL,
  biography    CLOB,
  created_at   TIMESTAMP DEFAULT SYSTIMESTAMP --
);
