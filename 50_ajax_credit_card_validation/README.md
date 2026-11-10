# Q50 – Ajax Credit Card Validation (PHP `preg_match`)

**Question 50:** Write a web application using Ajax to do the following:
A. check to make sure that the credit card number is composed of exactly 16 numerical digits
B. check to make sure that a Visa card number starts with a "4" and a MasterCard number starts
with a "5". You can check for these things using regular expressions in combination with the
PHP function `preg_match`. A really good regex will allow for an optional "-" between every
grouping of 4 numbers. For example, 4111111111111111 and 4111-1111-1111-1111 would both be
valid credit card numbers. If the user has not supplied a card number with the correct number
of digits, show an error message.

## Files

| File | Purpose |
|------|---------|
| `index.html` | Card-type `<select>` + number input; sends the data with `XMLHttpRequest` and shows the result without reloading |
| `validate.php` | Validates with `preg_match()` and returns `VALID`/`INVALID` + a message (plain text) |

## Regular expressions used

| Check | Regex | Meaning |
|-------|-------|---------|
| A. 16 digits, optional dashes | `/^\d{4}(-?)\d{4}\1\d{4}\1\d{4}$/` | four groups of 4 digits; `(-?)` captures "-" or nothing, `\1` makes every later separator the same |
| B. Visa | `/^4/` | must start with 4 |
| B. MasterCard | `/^5/` | must start with 5 |

So `4111111111111111` and `4111-1111-1111-1111` are accepted, while `4111-11111111-1111`
(mixed separators) is rejected. If the format check fails, the script counts the digits and
reports how many were entered.

## How it works (Ajax flow)

1. The user picks Visa/MasterCard, types the number and clicks **Validate**.
2. JavaScript cancels the normal submit and sends
   `POST validate.php` with `type=...&number=...` using `XMLHttpRequest`.
3. `validate.php` runs the `preg_match()` checks and echoes the result.
4. In `onreadystatechange` (`readyState == 4`, `status == 200`) the message is written into
   the `#result` box (green = valid, red = error) — the page is not reloaded.

## How to run

PHP built-in server (no Apache needed):

```bash
cd 50_ajax_credit_card_validation
php -S localhost:8000
```

Open <http://localhost:8000/index.html>.
(Or copy the folder into `htdocs` of XAMPP/WAMP and open
`http://localhost/50_ajax_credit_card_validation/`.)

Test the PHP script directly with curl:

```bash
curl -d "type=visa&number=4111-1111-1111-1111" http://localhost:8000/validate.php
```

## Sample output (tested with `php -S` + `curl`)

| Type | Number | Result |
|------|--------|--------|
| Visa | `4111111111111111` | Valid Visa card number (\*\*\*\*\*\*\*\*\*\*\*\*1111). |
| Visa | `4111-1111-1111-1111` | Valid Visa card number (\*\*\*\*\*\*\*\*\*\*\*\*1111). |
| MasterCard | `5555-5555-5555-4444` | Valid MasterCard card number (\*\*\*\*\*\*\*\*\*\*\*\*4444). |
| MasterCard | `4111111111111111` | Error: a MasterCard card number must start with "5". |
| Visa | `5555555555554444` | Error: a Visa card number must start with "4". |
| Visa | `411111111111` | Error: a card number must have exactly 16 digits (you entered 12). |
| Visa | `4111-11111111-1111` | Error: use either no dashes or a "-" between every group of 4 digits (e.g. 4111-1111-1111-1111). |
| Visa | `4111x111-1111-1111` | Error: the card number may contain only digits and "-". |
| Visa | *(empty)* | Please enter a credit card number. |
