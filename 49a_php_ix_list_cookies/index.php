<?php
/*
 * Question 49 (a)(ix): Implement using PHP: A web application that lists all cookies stored
 * in the browser on clicking "List Cookies" button. Add cookies if necessary.
 *
 * index.php - one page that
 *   1. sets two sample cookies on the first visit (so there is always something to list),
 *   2. lets the user add a cookie (name + value, valid for 1 hour),
 *   3. lists every cookie the browser sent ($_COOKIE) when "List Cookies" is clicked.
 */

$message = '';

// 1. Sample cookies on the first visit
if (empty($_COOKIE) && !isset($_GET['list'])) {
    setcookie('course', 'MCA', time() + 3600, '/');
    setcookie('subject', 'Web Technologies', time() + 3600, '/');
    $message = 'Sample cookies "course" and "subject" were set. Click "List Cookies" to see them.';
}

// 2. Add a cookie
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $name  = trim($_POST['cname'] ?? '');
    $value = $_POST['cvalue'] ?? '';
    if (!preg_match('/^[A-Za-z0-9_-]{1,50}$/', $name)) {
        $message = 'Cookie name may contain only letters, digits, "_" and "-".';
    } else {
        setcookie($name, $value, time() + 3600, '/');
        // Redirect so the new cookie is sent back by the browser and the form is not re-posted
        header('Location: index.php?added=' . urlencode($name));
        exit;
    }
}

if (isset($_GET['added'])) {
    $message = 'Cookie "' . $_GET['added'] . '" added (expires in 1 hour).';
}

$showList = isset($_GET['list']);
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>List Cookies</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 460px; margin: 40px auto; padding: 20px 25px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; }
        h2 { text-align: center; margin-top: 0; }
        label { display: block; margin-top: 8px; }
        input[type=text] { width: 100%; padding: 6px; box-sizing: border-box; }
        button { margin-top: 12px; padding: 8px 16px; cursor: pointer; }
        .msg { color: #0645ad; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #999; padding: 6px; text-align: left; word-break: break-all; }
        th { background: #ddd; }
    </style>
</head>
<body>
    <div class="box">
        <h2>Cookies</h2>
<?php if ($message !== ''): ?>
        <p class="msg"><?= htmlspecialchars($message) ?></p>
<?php endif; ?>

        <form action="index.php" method="post">
            <label for="cname">Cookie Name</label>
            <input type="text" id="cname" name="cname" required>
            <label for="cvalue">Cookie Value</label>
            <input type="text" id="cvalue" name="cvalue">
            <button type="submit">Add Cookie</button>
        </form>

        <form action="index.php" method="get">
            <button type="submit" name="list" value="1">List Cookies</button>
        </form>

<?php if ($showList): ?>
    <?php if (empty($_COOKIE)): ?>
        <p>No cookies are stored for this site.</p>
    <?php else: ?>
        <table>
            <tr><th>#</th><th>Cookie Name</th><th>Cookie Value</th></tr>
        <?php $i = 1; foreach ($_COOKIE as $cname => $cvalue): ?>
            <tr>
                <td><?= $i++ ?></td>
                <td><?= htmlspecialchars($cname) ?></td>
                <td><?= htmlspecialchars(is_array($cvalue) ? json_encode($cvalue) : $cvalue) ?></td>
            </tr>
        <?php endforeach; ?>
        </table>
        <p>Total cookies: <?= count($_COOKIE) ?></p>
    <?php endif; ?>
<?php endif; ?>
    </div>
</body>
</html>
