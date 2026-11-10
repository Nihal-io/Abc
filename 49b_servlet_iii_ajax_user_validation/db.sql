-- Question 49 (b) iii) - Servlets: AJAX user validation against a database.
-- Creates the database and the users table with some sample users.
CREATE DATABASE IF NOT EXISTS labdb;
USE labdb;

CREATE TABLE IF NOT EXISTS users (
  id       INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50)  NOT NULL UNIQUE,
  password VARCHAR(50)  NOT NULL,
  fullname VARCHAR(100) NOT NULL
);

INSERT IGNORE INTO users (username, password, fullname) VALUES
  ('admin', 'admin123', 'Administrator'),
  ('ravi',  'ravi@123', 'Ravi Kumar'),
  ('priya', 'priya@123', 'Priya Sharma');
