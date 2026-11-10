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
 * login.php - decides between: welcome page / password mismatch page / registration page.
 */
require 'db_config.php';
session_start();

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: index.html');
    exit;
}

$username = trim($_POST['username'] ?? '');
$password = $_POST['password'] ?? '';

if ($username === '' || $password === '') {
    header('Location: index.html');
    exit;
}

// Hint from the question: keep the submitted login name and password in the session,
// so that register.php can use them after the user enters the full name.
$_SESSION['username'] = $username;
$_SESSION['password'] = $password;

$conn = get_connection();
$stmt = $conn->prepare('SELECT password, fullname FROM users WHERE username = ?');
$stmt->bind_param('s', $username);
$stmt->execute();
$row = $stmt->get_result()->fetch_assoc();
$stmt->close();
$conn->close();

if ($row === null) {
    $page = 'register';                     // name not found
} elseif ($row['password'] === $password) {
    $page = 'welcome';                      // name and password match
    unset($_SESSION['password']);
} else {
    $page = 'mismatch';                     // name found, wrong password
    unset($_SESSION['username'], $_SESSION['password']);
}

$titles = ['register' => 'Registration', 'welcome' => 'Welcome', 'mismatch' => 'Password Mismatch'];
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><?= $titles[$page] ?></title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 360px; margin: 60px auto; padding: 20px 25px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
        .ok { color: green; }
        .fail { color: #c00; }
        label { display: block; margin-top: 10px; text-align: left; }
        input[type=text] { width: 100%; padding: 6px; box-sizing: border-box; }
        input[type=submit] { margin-top: 15px; width: 100%; padding: 8px; cursor: pointer; }
    </style>
</head>
<body>
    <div class="box">
<?php if ($page === 'welcome'): ?>
        <h2 class="ok">Welcome, <?= htmlspecialchars($row['fullname']) ?>!</h2>
        <p>You have logged in successfully as <strong><?= htmlspecialchars($username) ?></strong>.</p>
        <p><a href="index.html">Logout</a></p>

<?php elseif ($page === 'mismatch'): ?>
        <h2 class="fail">Password Mismatch</h2>
        <p>The password entered for <strong><?= htmlspecialchars($username) ?></strong> is incorrect.</p>
        <p><a href="index.html">Try again</a></p>

<?php else: ?>
        <h2>Registration</h2>
        <p>The login name <strong><?= htmlspecialchars($username) ?></strong> was not found.
           Enter your full name to register.</p>
        <form action="register.php" method="post">
            <label for="fullname">Full Name</label>
            <input type="text" id="fullname" name="fullname" required>
            <input type="submit" value="Register">
        </form>
        <p><a href="index.html">Cancel</a></p>
<?php endif; ?>
    </div>
</body>
</html>
