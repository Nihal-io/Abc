<?php
/*
 * Question 49 (a)(vii): Implement using PHP: A web application that takes name and age from an
 * HTML page. If the age is less than 18, it should send a page with "Hello <name>, you are not
 * authorized to visit this site" message, where <name> should be replaced with the entered name.
 * Otherwise it should send "Welcome <name> to this site" message.
 *
 * (Repeats (vi) in the question paper.) check.php - this version reads the values from the
 * query string ($_GET) and validates them on the server before deciding.
 */

$name   = trim($_GET['name'] ?? '');
$ageRaw = trim($_GET['age'] ?? '');
$errors = [];

if ($name === '') {
    $errors[] = 'Name is required.';
}

// Age must be a whole number from 0 to 150
$age = filter_var($ageRaw, FILTER_VALIDATE_INT, ['options' => ['min_range' => 0, 'max_range' => 150]]);
if ($ageRaw === '') {
    $errors[] = 'Age is required.';
} elseif ($age === false) {
    $errors[] = 'Age must be a whole number between 0 and 150.';
}

if (!$errors) {
    if ($age < 18) {
        $message = "Hello $name, you are not authorized to visit this site";
        $class   = 'deny';
    } else {
        $message = "Welcome $name to this site";
        $class   = 'allow';
    }
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
        .deny, .error { color: #c00; }
        ul { text-align: left; }
    </style>
</head>
<body>
    <div class="box">
<?php if ($errors): ?>
        <h2 class="error">Invalid input</h2>
        <ul>
<?php foreach ($errors as $e): ?>
            <li class="error"><?= htmlspecialchars($e) ?></li>
<?php endforeach; ?>
        </ul>
<?php else: ?>
        <h2 class="<?= $class ?>"><?= htmlspecialchars($message) ?></h2>
<?php endif; ?>
        <p><a href="index.html">Back</a></p>
    </div>
</body>
</html>
