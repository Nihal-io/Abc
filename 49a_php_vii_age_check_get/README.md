# 49 (a)(vii) – Age check, GET request with validation (PHP)

**Question:** A web application that takes name and age from an HTML page. If the age is less
than 18, it should send a page with "Hello &lt;name&gt;, you are not authorized to visit this site"
message, where &lt;name&gt; should be replaced with the entered name. Otherwise it should send
"Welcome &lt;name&gt; to this site" message.

> **Note:** sub-part (vii) repeats sub-part (vi) word for word in the question paper. So that the
> record does not contain the same program twice, this version solves the same requirement a
> different way:
> - the form uses **GET**, so the data travels in the query string
>   (`check.php?name=Ravi&age=15`) and is read from `$_GET`;
> - the server **validates** the input: name must not be empty, and age must be a whole,
>   non-negative number (0–150), checked with `filter_var(..., FILTER_VALIDATE_INT)`.
>
> The POST version is in `../49a_php_vi_age_check/`.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Form with name and age, `method="get"`, action `check.php` |
| `check.php` | Reads and validates `$_GET['name']`, `$_GET['age']`; shows the message or the list of errors |

## How to run
1. Put the folder in the web root or run `php -S localhost:8000` inside it.
2. Open `http://localhost:8000/index.html` – or type the URL directly, e.g.
   `http://localhost:8000/check.php?name=Ravi&age=15`.

## Sample input / output
| URL | Result page |
|-----|-------------|
| `check.php?name=Ravi&age=15` | Hello Ravi, you are not authorized to visit this site |
| `check.php?name=Anita&age=25` | Welcome Anita to this site |
| `check.php?name=Kiran&age=abc` | Invalid input – Age must be a whole number between 0 and 150. |
| `check.php?name=Kiran&age=-4` | Invalid input – Age must be a whole number between 0 and 150. |
| `check.php?age=20` | Invalid input – Name is required. |
