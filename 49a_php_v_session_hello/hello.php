<?php
/*
 * Question 49 (a)(v): Implement using PHP: A web application takes a name as input and on submit
 * it shows a hello<name> page where <name> is taken from the request. It shows the start time at
 * the right top corner of the page and provides the logout button. On clicking this button, it
 * should show a logout page with Thank You<name> message with the duration of usage.
 * (Use session to store name and time).
 *
 * hello.php - stores the name and start time in the session and shows the Hello page.
 */
date_default_timezone_set('Asia/Kolkata');
session_start();

if ($_SERVER['REQUEST_METHOD'] === 'POST' && trim($_POST['name'] ?? '') !== '') {
    session_regenerate_id(true);           // new session id for the new "login"
    $_SESSION['name']  = trim($_POST['name']);
    $_SESSION['start'] = time();
}

// No name in the session -> go back to the form
if (!isset($_SESSION['name'])) {
    header('Location: index.html');
    exit;
}

$name  = $_SESSION['name'];
$start = $_SESSION['start'];
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hello <?= htmlspecialchars($name) ?></title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; margin: 0; }
        .start-time { position: absolute; top: 10px; right: 15px; padding: 6px 10px;
                      background: #fff; border: 1px solid #ccc; border-radius: 4px; font-size: 0.9em; }
        .box { width: 360px; margin: 80px auto; padding: 20px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
        button { padding: 8px 20px; cursor: pointer; }
    </style>
</head>
<body>
    <div class="start-time">Start time: <?= date('d-m-Y h:i:s A', $start) ?></div>

    <div class="box">
        <h1>Hello <?= htmlspecialchars($name) ?></h1>
        <p>You can browse the site. Click Logout when you are done.</p>
        <form action="logout.php" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>
</body>
</html>
