# 49 (a)(ii) – User validation using an XML file (PHP)

**Question:** Modify the above program (49 (a)(i) user validation) to use an XML file instead of
database.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login form, POSTs to `login.php` |
| `login.php` | Loads `users.xml` with `simplexml_load_file()`, loops over the `<user>` elements and compares username and password |
| `users.xml` | The list of valid users (`<username>`, `<password>`, `<fullname>`) |

No database is needed.

## How to run
1. Copy the folder into the web root (XAMPP `htdocs/`, `/var/www/html/`), or run
   `php -S localhost:8000` inside the folder.
2. Open `http://localhost:8000/index.html`.
3. To add users, add another `<user>` block to `users.xml`.

## Sample input / output
| Login name | Password | Result |
|------------|----------|--------|
| `student` | `pass123` | **Login Successful** – Welcome, Ravi Kumar (student)! |
| `anita` | `anita@1` | **Login Successful** – Welcome, Anita Sharma (anita)! |
| `admin` | `xyz` | **Login Failed** – Invalid login name or password. |

## Notes
- Output is escaped with `htmlspecialchars`.
- Keep `users.xml` outside the public web root (or block it in the server config) in a real
  deployment, otherwise anyone can download it; storing `password_hash()` values instead of
  plain-text passwords would also be better.
