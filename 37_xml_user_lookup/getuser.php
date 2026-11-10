<?php
/*
 * Question 37: Create and save an XML document at the server, which contains 10 users information.
 * Write a program which takes User Id as input and returns the user details by taking the user
 * information from the XML document.
 *
 * getuser.php - server-side version. Loads users.xml (stored on the server) with SimpleXML and
 * returns the details of the user whose <userid> equals ?id=...
 *   getuser.php?id=103              -> HTML page with a search form and the result
 *   getuser.php?id=103&format=json  -> JSON (used by the "Search via PHP" button in index.html)
 * Run:  php -S localhost:8000   (inside this folder)  then open http://localhost:8000/getuser.php
 */

$xmlFile = __DIR__ . '/users.xml';
$id      = isset($_GET['id']) ? trim($_GET['id']) : '';
$format  = isset($_GET['format']) ? $_GET['format'] : 'html';

$labels = [
    'userid' => 'User ID', 'name' => 'Name', 'email' => 'E-mail', 'phone' => 'Phone',
    'city'   => 'City',    'course' => 'Course', 'dob' => 'Date of Birth'
];

$found   = null;     // array of details when the user exists
$message = '';

if ($id === '') {
    $message = 'Please enter a User ID.';
} elseif (!ctype_digit($id)) {
    $message = 'User ID must be a number.';
} else {
    $xml = @simplexml_load_file($xmlFile);
    if ($xml === false) {
        $message = 'Could not read users.xml on the server.';
    } else {
        // XPath query: find the <user> whose <userid> child matches the input
        $result = $xml->xpath("/users/user[userid='" . $id . "']");
        if ($result) {
            $user  = $result[0];
            $found = [];
            foreach ($labels as $tag => $label) {
                $found[$label] = (string) $user->$tag;
            }
        } else {
            $message = "No user found with ID $id.";
        }
    }
}

// ---------- JSON response ----------
if ($format === 'json') {
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($found
        ? ['found' => true, 'user' => $found]
        : ['found' => false, 'message' => $message]);
    exit;
}

// ---------- HTML response ----------
function h($s) { return htmlspecialchars($s, ENT_QUOTES, 'UTF-8'); }
?>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Q37 - User Details (PHP + XML)</title>
<style>
  body { font-family: Arial, sans-serif; background: #eef2f6; margin: 0; padding: 30px 10px; }
  .box { background: #fff; max-width: 520px; margin: auto; padding: 20px 26px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,.2); }
  h2 { margin-top: 0; text-align: center; color: #2c3e50; }
  input { padding: 7px; font-size: 15px; width: 160px; }
  button { padding: 7px 14px; font-size: 14px; cursor: pointer; }
  table { border-collapse: collapse; width: 100%; margin-top: 14px; }
  th, td { border: 1px solid #bbb; padding: 6px 8px; text-align: left; }
  th { background: #34495e; color: #fff; width: 35%; }
  .error { color: #c0392b; font-weight: bold; }
</style>
</head>
<body>
<div class="box">
  <h2>User Details (PHP SimpleXML)</h2>
  <form method="get" action="getuser.php">
    <label for="id">User ID:</label>
    <input type="text" id="id" name="id" value="<?= h($id) ?>" placeholder="101 to 110">
    <button type="submit">Get Details</button>
  </form>

  <?php if ($found): ?>
    <table>
      <?php foreach ($found as $label => $value): ?>
        <tr><th><?= h($label) ?></th><td><?= h($value) ?></td></tr>
      <?php endforeach; ?>
    </table>
  <?php elseif (isset($_GET['id'])): ?>
    <p class="error"><?= h($message) ?></p>
  <?php endif; ?>
</div>
</body>
</html>
