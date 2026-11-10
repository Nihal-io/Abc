<?php
/*
 * Question 49 (a)(iv): Implement using PHP: A simple calculator application that takes two
 * numbers and an operator (+, -, *, /, %) from an HTML page and returns the result page with
 * the operation performed on the operands.
 *
 * calculate.php - performs the operation and shows the result page.
 */

$num1 = $_POST['num1'] ?? '';
$num2 = $_POST['num2'] ?? '';
$op   = $_POST['op'] ?? '';

$error  = '';
$result = null;

if (!is_numeric($num1) || !is_numeric($num2)) {
    $error = 'Please enter two valid numbers.';
} else {
    $a = (float) $num1;
    $b = (float) $num2;
    switch ($op) {
        case '+':
            $result = $a + $b;
            break;
        case '-':
            $result = $a - $b;
            break;
        case '*':
            $result = $a * $b;
            break;
        case '/':
            if ($b == 0) {
                $error = 'Division by zero is not allowed.';
            } else {
                $result = $a / $b;
            }
            break;
        case '%':
            if ($b == 0) {
                $error = 'Modulus by zero is not allowed.';
            } else {
                $result = fmod($a, $b);   // fmod() also works for decimal numbers
            }
            break;
        default:
            $error = 'Invalid operator.';
    }
}

// Show whole numbers without ".0", others with up to 6 decimals.
function show_number($n)
{
    return rtrim(rtrim(number_format($n, 6, '.', ''), '0'), '.');
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calculator Result</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f0f2f5; }
        .box { width: 360px; margin: 60px auto; padding: 20px; background: #fff;
               border: 1px solid #ccc; border-radius: 6px; text-align: center; }
        .result { font-size: 1.4em; color: #0645ad; }
        .error { color: #c00; }
    </style>
</head>
<body>
    <div class="box">
        <h2>Calculator Result</h2>
<?php if ($error !== ''): ?>
        <p class="error">Error: <?= htmlspecialchars($error) ?></p>
<?php else: ?>
        <p class="result">
            <?= htmlspecialchars(show_number($a)) ?> <?= htmlspecialchars($op) ?>
            <?= htmlspecialchars(show_number($b)) ?> = <strong><?= htmlspecialchars(show_number($result)) ?></strong>
        </p>
<?php endif; ?>
        <p><a href="index.html">Back to Calculator</a></p>
    </div>
</body>
</html>
