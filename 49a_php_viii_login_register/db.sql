-- Question 49 (a)(viii): Implement using PHP: A web application for implementation:
-- The user is first served a login page which takes user's name and password. After submitting
-- the details the server checks these values against the data from a database and takes the
-- following decisions. If name and password matches serves a welcome page with user's full name.
-- If name and password doesn't match, then serves "password mismatch" page. If name is not found
-- in the database, serves a registration page, where user's full name is asked and on submitting
-- the full name, it stores the login name, password and full name in the database (hint: use
-- session for storing data, submitted login name and password).
--
-- Database setup. Run with:  mysql -u root -p < db.sql

CREATE DATABASE IF NOT EXISTS labdb;
USE labdb;

CREATE TABLE IF NOT EXISTS users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    fullname VARCHAR(100) NOT NULL
);

-- Sample users (plain-text passwords, for the lab only)
INSERT IGNORE INTO users (username, password, fullname) VALUES
    ('admin',   'admin123', 'Administrator'),
    ('student', 'pass123',  'Ravi Kumar'),
    ('anita',   'anita@1',  'Anita Sharma');
