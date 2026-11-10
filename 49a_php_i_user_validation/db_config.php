<?php
/*
 * Question 49 (a)(i): Implement the following web applications using PHP:
 * A user validation web application, where the user submits the login name and password
 * to the server. The name and password are checked against the data already available in
 * Database and if the data matches, a successful login page is returned. Otherwise a failure
 * message is shown to the user.
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
