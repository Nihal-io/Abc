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
 * register.php - takes the login name and password from the session and the full name from
 * the form, and stores all three in the users table.
 */
require 'db_config.php';
session_start();

// Must come here from login.php (login name and password are in the session)
if ($_SERVER['REQUEST_METHOD'] !== 'POST'
        || !isset($_SESSION['username'], $_SESSION['password'])) {
    header('Location: index.html');
    exit;
}

$username = $_SESSION['username'];
$password = $_SESSION['password'];
$fullname = trim($_POST['fullname'] ?? '');

$error = '';
if ($fullname === '') {
    $error = 'Full name is required.';
} else {
    $conn = get_connection();
    try {
        $stmt = $conn->prepare('INSERT INTO users (username, password, fullname) VALUES (?, ?, ?)');
        $stmt->bind_param('sss', $username, $password, $fullname);
        $stmt->execute();
        $stmt->close();
    } catch (mysqli_sql_exception $e) {
        // 1062 = duplicate entry (someone registered this login name meanwhile)
        $error = ($e->getCode() == 1062) ? 'This login name is already registered.'
                                         : 'Could not register: ' . $e->getMessage();
    }
    $conn->close();
}

// Registration finished (or failed): the saved login data is no longer needed
unset($_SESSION['username'], $_SESSION['password']);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= $error ? 'Registration Failed' : 'Registered' ?></title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 360px; margin: 60px auto; padding: 20px 25px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
        .ok { color: green; }
        .fail { color: #c00; }
    </style>
</head>
<body>
    <div class="box">
<?php if ($error): ?>
        <h2 class="fail">Registration Failed</h2>
        <p><?= htmlspecialchars($error) ?></p>
<?php else: ?>
        <h2 class="ok">Registration Successful</h2>
        <p>Welcome, <strong><?= htmlspecialchars($fullname) ?></strong>!</p>
        <p>Your login name <strong><?= htmlspecialchars($username) ?></strong> has been saved.
           You can now log in with it.</p>
<?php endif; ?>
        <p><a href="index.html">Go to Login</a></p>
    </div>
</body>
</html>
