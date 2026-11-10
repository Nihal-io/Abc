<?php
/*
 * Question 49 (a)(viii): Implement using PHP: A web application for implementation:
 * The user is first served a login page which takes user's name and password. After submitting
 * the details the server checks these values against the data from a database and takes the
 * following decisions. If name and password matches serves a welcome page with user's full name.
 * If name and password doesn't match, then serves "password mismatch" page. If name is not found
 * in the database, serves a registration page, where user's full name is asked and on submitting
 * the full name, it stores the login name, password and full name in the database (hint: use
 * session for storing data, submitted login name and password).
 *
 * db_config.php - database connection settings (change them to match your MySQL server).
 */

define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', 'root');
define('DB_NAME', 'labdb');

// Returns a mysqli connection, or stops with an error message.
function get_connection()
{
    mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
    try {
        $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
        $conn->set_charset('utf8mb4');
        return $conn;
    } catch (mysqli_sql_exception $e) {
        http_response_code(500);
        exit('Database connection failed: ' . htmlspecialchars($e->getMessage()));
    }
}
