<?php
/*
 * Question 50: Write a web application using Ajax to do the following:
 *   A. check to make sure that the credit card number is composed of exactly 16 numerical digits
 *   B. check to make sure that a Visa card number starts with a "4" and a MasterCard number
 *      starts with a "5".
 * You can check for these things using regular expressions in combination with the PHP function
 * preg_match. A really good regex will allow for an optional "-" between every grouping of 4
 * numbers. For example, 4111111111111111 and 4111-1111-1111-1111 would both be valid credit card
 * numbers. If the user has not supplied a card number with the correct number of digits, show an
 * error message.
 *
 * validate.php - server side of the Ajax application. It receives "type" (visa / mastercard)
 * and "number" (GET or POST), validates the number with preg_match() and prints a short
 * message that index.html shows without reloading the page.
 * The first line of the reply is "VALID" or "INVALID", the second line is the message.
 */

header('Content-Type: text/plain; charset=UTF-8');

$type   = isset($_REQUEST['type'])   ? strtolower(trim($_REQUEST['type'])) : '';
$number = isset($_REQUEST['number']) ? trim($_REQUEST['number']) : '';

// 16 digits in 4 groups of 4. "(-?)" captures either "-" or nothing after the first group
// and "\1" forces the same separator between all the remaining groups, so
// 4111111111111111 and 4111-1111-1111-1111 are valid but 4111-11111111-1111 is not.
$formatRegex = '/^\d{4}(-?)\d{4}\1\d{4}\1\d{4}$/';

// Card type rules: Visa starts with 4, MasterCard starts with 5
$typeRules = array(
    'visa'       => array('name' => 'Visa',       'regex' => '/^4/', 'digit' => '4'),
    'mastercard' => array('name' => 'MasterCard', 'regex' => '/^5/', 'digit' => '5'),
);

function reply($ok, $message)
{
    echo ($ok ? "VALID" : "INVALID") . "\n" . $message;
    exit;
}

if ($number === '') {
    reply(false, 'Please enter a credit card number.');
}

if (!isset($typeRules[$type])) {
    reply(false, 'Please select a card type (Visa or MasterCard).');
}

// A. exactly 16 digits (optionally grouped 4-4-4-4 with "-")
if (!preg_match($formatRegex, $number)) {
    $digits = preg_replace('/\D/', '', $number);   // count only the digits for the message
    if (preg_match('/[^\d-]/', $number)) {
        reply(false, 'Error: the card number may contain only digits and "-".');
    }
    if (strlen($digits) != 16) {
        reply(false, 'Error: a card number must have exactly 16 digits (you entered '
            . strlen($digits) . ').');
    }
    reply(false, 'Error: use either no dashes or a "-" between every group of 4 digits '
        . '(e.g. 4111-1111-1111-1111).');
}

// B. Visa must start with 4, MasterCard with 5
$rule = $typeRules[$type];
if (!preg_match($rule['regex'], $number)) {
    reply(false, 'Error: a ' . $rule['name'] . ' card number must start with "' . $rule['digit'] . '".');
}

$plain = str_replace('-', '', $number);
$masked = str_repeat('*', 12) . substr($plain, -4);
reply(true, 'Valid ' . $rule['name'] . ' card number (' . $masked . ').');
