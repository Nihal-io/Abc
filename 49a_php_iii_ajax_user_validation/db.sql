-- Question 49 (a)(iii): Modify the above program (49 (a)(i) user validation against the
-- Database) using AJAX to show the result on the same page below the submit button.
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
