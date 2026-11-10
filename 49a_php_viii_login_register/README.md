# 49 (a)(viii) – Login with welcome / password mismatch / registration (PHP + MySQL)

**Question:** The user is first served a login page which takes user's name and password. After
submitting the details the server checks these values against the data from a database and
takes the following decisions. If name and password matches serves a welcome page with user's
full name. If name and password doesn't match, then serves "password mismatch" page. If name is
not found in the database, serves a registration page, where user's full name is asked and on
submitting the full name, it stores the login name, password and full name in the database
(hint: use session for storing data, submitted login name and password).

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login page (login name + password), POSTs to `login.php` |
| `login.php` | Saves the login name and password in `$_SESSION`, looks the name up with a prepared statement and serves one of three pages: **welcome** (full name), **password mismatch**, or **registration** (asks for full name, POSTs to `register.php`) |
| `register.php` | Reads login name and password from the session and full name from the form, `INSERT`s them into `users`, then shows a success page |
| `db_config.php` | Database settings (`labdb`, `root`/`root`) |
| `db.sql` | Creates `labdb.users(id, username UNIQUE, password, fullname)` with sample rows |

## Flow
```
index.html --POST--> login.php
                       |-- name found, password OK    -> "Welcome, <full name>!"
                       |-- name found, password wrong -> "Password Mismatch"
                       `-- name not found             -> registration form
                                                          --POST fullname--> register.php
                                                          (username + password taken from session)
                                                          -> INSERT -> "Registration Successful"
```

## How to run
1. `mysql -u root -p < db.sql` (edit `db_config.php` if your MySQL login is not `root`/`root`).
2. Put the folder in the web root or run `php -S localhost:8000` inside it.
3. Open `http://localhost:8000/index.html`.

## Sample input / output
| Login name | Password | Result |
|------------|----------|--------|
| `student` | `pass123` | Welcome, Ravi Kumar! |
| `student` | `abc` | Password Mismatch |
| `priya` | `priya99` | Registration page → enter full name `Priya Nair` → "Registration Successful – Welcome, Priya Nair!" |
| `priya` | `priya99` (again) | Welcome, Priya Nair! |

## Notes
- All queries use mysqli prepared statements; all output goes through `htmlspecialchars`.
- Passwords are stored as plain text to keep the lab simple. A better version would store
  `password_hash($password, PASSWORD_DEFAULT)` in `register.php` and check it with
  `password_verify($password, $row['password'])` in `login.php`.
