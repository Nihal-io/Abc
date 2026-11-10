-- Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
-- Database for the Online Student Admission web application (run: mysql -u root -p < db.sql)

CREATE DATABASE IF NOT EXISTS labdb;
USE labdb;

CREATE TABLE IF NOT EXISTS admissions (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    -- page 1: personal details
    name          VARCHAR(50)  NOT NULL,
    gender        VARCHAR(10)  NOT NULL,
    dob           DATE         NOT NULL,
    email         VARCHAR(60)  NOT NULL,
    phone         CHAR(10)     NOT NULL,
    address       VARCHAR(200) NOT NULL,
    -- page 2: academic details
    qualification VARCHAR(40)  NOT NULL,
    university    VARCHAR(80)  NOT NULL,
    pass_year     INT          NOT NULL,
    percentage    DECIMAL(5,2) NOT NULL,
    -- page 3: course selection
    course        VARCHAR(40)  NOT NULL,
    study_mode    VARCHAR(15)  NOT NULL,
    hostel        BOOLEAN      NOT NULL DEFAULT FALSE,
    applied_on    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Sample rows
INSERT INTO admissions (name, gender, dob, email, phone, address, qualification, university,
                        pass_year, percentage, course, study_mode, hostel)
VALUES
 ('Anu Mathew', 'Female', '2003-04-12', 'anu@example.com', '9876543210', 'Kochi, Kerala',
  'BCA', 'MG University', 2024, 82.50, 'MCA', 'Regular', TRUE),
 ('Rahul Nair', 'Male', '2002-09-30', 'rahul@example.com', '9123456780', 'Thrissur, Kerala',
  'B.Sc Computer Science', 'Calicut University', 2023, 76.40, 'M.Sc Data Science', 'Regular', FALSE);

SELECT * FROM admissions;
