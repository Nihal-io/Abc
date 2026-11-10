-- Question 47: Creation of an application to have access from a database using JDBC.
-- Database setup for JdbcDemo.java (run: mysql -u root -p < db.sql)

CREATE DATABASE IF NOT EXISTS labdb;
USE labdb;

CREATE TABLE IF NOT EXISTS students (
    roll_no INT PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    course  VARCHAR(30),
    marks   INT
);

-- Sample data (INSERT IGNORE so the script can be run more than once)
INSERT IGNORE INTO students (roll_no, name, course, marks) VALUES
    (101, 'Anu Mathew',  'MCA', 85),
    (102, 'Rahul Nair',  'MCA', 78),
    (103, 'Sneha Menon', 'MCA', 91);

SELECT * FROM students;
