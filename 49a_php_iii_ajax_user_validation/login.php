<?php
/*
 * Question 49 (a)(iii): Modify the above program (49 (a)(i): a user validation web application,
 * where the login name and password are checked against the data in the Database; on a match a
 * successful login message is returned, otherwise a failure message is shown) using AJAX to show
 * the result on the same page below the submit button.
 *
 * login.php - called by XMLHttpRequest from index.html. Checks the users table and returns
 * a small JSON answer: {"success": true|false, "message": "..."}
 */
require 'db_config.php';

header('Content-Type: application/json; charset=UTF-8');

$username = trim($_POST['username'] ?? '');
$password = $_POST['password'] ?? '';

if ($username === '' || $password === '') {
    echo json_encode(['success' => false, 'message' => 'Please enter login name and password.']);
    exit;
}

$conn = get_connection();
$stmt = $conn->prepare('SELECT fullname FROM users WHERE username = ? AND password = ?');
$stmt->bind_param('ss', $username, $password);
$stmt->execute();
$row = $stmt->get_result()->fetch_assoc();
$stmt->close();
$conn->close();

if ($row) {
    // The page puts this text in with textContent, so no HTML escaping is needed here.
    echo json_encode(['success' => true,
                      'message' => 'Login successful. Welcome, ' . $row['fullname'] . '!']);
} else {
    echo json_encode(['success' => false, 'message' => 'Login failed: invalid login name or password.']);
}
