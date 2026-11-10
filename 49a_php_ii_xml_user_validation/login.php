<?php
/*
 * Question 49 (a)(ii): Modify the above program (49 (a)(i): a user validation web application,
 * where the user submits the login name and password to the server; if they match the stored
 * data a successful login page is returned, otherwise a failure message is shown) to use an
 * XML file instead of database.
 *
 * login.php - reads users.xml with SimpleXML and checks the submitted name/password.
 */

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: index.html');
    exit;
}

$username = trim($_POST['username'] ?? '');
$password = $_POST['password'] ?? '';

$xml = simplexml_load_file(__DIR__ . '/users.xml');
if ($xml === false) {
    http_response_code(500);
    exit('Could not read users.xml');
}

$fullname = null;
foreach ($xml->user as $user) {
    if ((string) $user->username === $username && (string) $user->password === $password) {
        $fullname = (string) $user->fullname;
        break;
    }
}
$success = ($fullname !== null);
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
        <p>Welcome, <strong><?= htmlspecialchars($fullname) ?></strong>
           (<?= htmlspecialchars($username) ?>)!</p>
<?php else: ?>
        <h2 class="fail">Login Failed</h2>
        <p>Invalid login name or password.</p>
<?php endif; ?>
        <p><a href="index.html">Back to Login</a></p>
    </div>
</body>
</html>
