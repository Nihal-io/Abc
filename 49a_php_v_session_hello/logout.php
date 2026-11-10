<?php
/*
 * Question 49 (a)(v): Implement using PHP: A web application takes a name as input and on submit
 * it shows a hello<name> page where <name> is taken from the request. It shows the start time at
 * the right top corner of the page and provides the logout button. On clicking this button, it
 * should show a logout page with Thank You<name> message with the duration of usage.
 * (Use session to store name and time).
 *
 * logout.php - reads name and start time from the session, computes the duration of usage,
 * destroys the session and shows the Thank You page.
 */
date_default_timezone_set('Asia/Kolkata');
session_start();

if (!isset($_SESSION['name'])) {
    header('Location: index.html');
    exit;
}

$name    = $_SESSION['name'];
$start   = $_SESSION['start'];
$end     = time();
$seconds = $end - $start;

$hours   = intdiv($seconds, 3600);
$minutes = intdiv($seconds % 3600, 60);
$secs    = $seconds % 60;
$duration = sprintf('%d hour(s) %d minute(s) %d second(s)', $hours, $minutes, $secs);

// End the session
$_SESSION = [];
if (ini_get('session.use_cookies')) {
    $p = session_get_cookie_params();
    setcookie(session_name(), '', time() - 3600, $p['path'], $p['domain'], $p['secure'], $p['httponly']);
}
session_destroy();
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logged Out</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 400px; margin: 80px auto; padding: 20px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
    </style>
</head>
<body>
    <div class="box">
        <h1>Thank You <?= htmlspecialchars($name) ?></h1>
        <p>Start time: <?= date('d-m-Y h:i:s A', $start) ?></p>
        <p>End time: <?= date('d-m-Y h:i:s A', $end) ?></p>
        <p>Duration of usage: <strong><?= $duration ?></strong></p>
        <p><a href="index.html">Start again</a></p>
    </div>
</body>
</html>
