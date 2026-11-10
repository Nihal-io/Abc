<?php
/*
 * Question 49 (a)(i): Implement the following web applications using PHP:
 * A user validation web application, where the user submits the login name and password
 * to the server. The name and password are checked against the data already available in
 * Database and if the data matches, a successful login page is returned. Otherwise a failure
 * message is shown to the user.
 *
 * login.php - checks the submitted name/password against the users table.
 */
require 'db_config.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: index.html');
    exit;
}

$username = trim($_POST['username'] ?? '');
$password = $_POST['password'] ?? '';

$conn = get_connection();
$stmt = $conn->prepare('SELECT fullname FROM users WHERE username = ? AND password = ?');
$stmt->bind_param('ss', $username, $password);
$stmt->execute();
$result = $stmt->get_result();
$row = $result->fetch_assoc();
$stmt->close();
$conn->close();

$success = ($row !== null);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= $success ? 'Login Successful' : 'Login Failed' ?></title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 380px; margin: 60px auto; padding: 20px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
        .ok { color: green; }
        .fail { color: #c00; }
    </style>
</head>
<body>
    <div class="box">
<?php if ($success): ?>
        <h2 class="ok">Login Successful</h2>
        <p>Welcome, <strong><?= htmlspecialchars($row['fullname']) ?></strong>
           (<?= htmlspecialchars($username) ?>)!</p>
<?php else: ?>
        <h2 class="fail">Login Failed</h2>
        <p>Invalid login name or password.</p>
<?php endif; ?>
        <p><a href="index.html">Back to Login</a></p>
    </div>
</body>
</html>
