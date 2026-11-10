-- Question 49 (a)(i): Implement the following web applications using PHP:
-- A user validation web application, where the user submits the login name and password
-- to the server. The name and password are checked against the data already available in
-- Database and if the data matches, a successful login page is returned. Otherwise a failure
-- message is shown to the user.
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
