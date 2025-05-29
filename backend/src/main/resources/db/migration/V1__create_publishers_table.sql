-- V1__create_publishers_table.sql
CREATE TABLE publishers (
  id           NUMBER PRIMARY KEY,
  name         VARCHAR2(100) NOT NULL,
  phone        VARCHAR2(20),
  email        VARCHAR2(100),
  address      VARCHAR2(200),
  created_at   TIMESTAMP DEFAULT SYSTIMESTAMP --
);
