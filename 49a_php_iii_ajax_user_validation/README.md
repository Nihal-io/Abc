# 49 (a)(iii) – User validation with AJAX (PHP + MySQL)

**Question:** Modify the above program (49 (a)(i) user validation against the database) using
AJAX to show the result on the same page below the submit button.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login form. JavaScript stops the normal submit, sends the data with `XMLHttpRequest` (POST) to `login.php`, and writes the answer into `<div id="result">` below the button |
| `login.php` | Checks the `users` table with a mysqli prepared statement and returns JSON `{"success": ..., "message": ...}` |
| `db_config.php` | Database settings (`labdb`, `root`/`root`) |
| `db.sql` | Creates `labdb.users` with sample rows (same as 49 (a)(i)) |

## How to run
1. `mysql -u root -p < db.sql` (skip if already done for 49 (a)(i)).
2. Edit `db_config.php` if your MySQL user/password are not `root`/`root`.
3. Put the folder in the web root or run `php -S localhost:8000` inside it.
4. Open `http://localhost:8000/index.html`.

## Sample input / output
The page is **not** reloaded; the message appears under the Login button:

| Login name | Password | Message below the button |
|------------|----------|--------------------------|
| `student` | `pass123` | Login successful. Welcome, Ravi Kumar! (green) |
| `student` | `abc` | Login failed: invalid login name or password. (red) |

Direct test of the server part:
```
curl -d "username=admin&password=admin123" http://localhost:8000/login.php
{"success":true,"message":"Login successful. Welcome, Administrator!"}
```

## Notes
- The browser inserts the message with `textContent`, so no HTML from the server is executed.
- Plain-text passwords are used for the lab; use `password_hash()` / `password_verify()` in real code.
