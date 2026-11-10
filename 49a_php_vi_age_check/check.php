<?php
/*
 * Question 49 (a)(vi): Implement using PHP: A web application that takes name and age from an
 * HTML page. If the age is less than 18, it should send a page with "Hello <name>, you are not
 * authorized to visit this site" message, where <name> should be replaced with the entered name.
 * Otherwise it should send "Welcome <name> to this site" message.
 *
 * check.php - reads name and age from the POST request and sends the matching message.
 */

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: index.html');
    exit;
}

$name = trim($_POST['name'] ?? '');
$age  = (int) ($_POST['age'] ?? 0);

if ($age < 18) {
    $message = "Hello $name, you are not authorized to visit this site";
    $class   = 'deny';
} else {
    $message = "Welcome $name to this site";
    $class   = 'allow';
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Age Check Result</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 420px; margin: 60px auto; padding: 20px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
        .allow { color: green; }
        .deny { color: #c00; }
    </style>
</head>
<body>
    <div class="box">
        <h2 class="<?= $class ?>"><?= htmlspecialchars($message) ?></h2>
        <p><a href="index.html">Back</a></p>
    </div>
</body>
</html>
