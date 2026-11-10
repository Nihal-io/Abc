# 49 (a)(vi) – Age check (PHP, POST form)

**Question:** A web application that takes name and age from an HTML page. If the age is less
than 18, it should send a page with "Hello &lt;name&gt;, you are not authorized to visit this site"
message, where &lt;name&gt; should be replaced with the entered name. Otherwise it should send
"Welcome &lt;name&gt; to this site" message.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Form with name and age, submitted with `method="post"` to `check.php` |
| `check.php` | Reads `$_POST['name']` and `$_POST['age']`, compares the age with 18 and sends the message page |

## How to run
1. Put the folder in the web root or run `php -S localhost:8000` inside it.
2. Open `http://localhost:8000/index.html`.

## Sample input / output
| Name | Age | Result page |
|------|-----|-------------|
| Ravi | 15 | Hello Ravi, you are not authorized to visit this site |
| Anita | 22 | Welcome Anita to this site |
| Kiran | 18 | Welcome Kiran to this site |

## Notes
- The entered name is escaped with `htmlspecialchars` before it is printed.
- See `../49a_php_vii_age_check_get/` for a second version that uses GET and validates the age.
