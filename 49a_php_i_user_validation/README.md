# 49 (a)(i) – User validation using PHP and MySQL

**Question:** A user validation web application, where the user submits the login name and
password to the server. The name and password are checked against the data already available in
Database and if the data matches, a successful login page is returned. Otherwise a failure
message is shown to the user.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login form (login name + password), POSTs to `login.php` |
| `login.php` | Looks the user up in the `users` table with a mysqli prepared statement and returns a success or failure page |
| `db_config.php` | Database settings (`labdb`, user `root`, password `root`) and `get_connection()` |
| `db.sql` | Creates database `labdb`, table `users(id, username UNIQUE, password, fullname)` and sample rows |

## How to run
1. Create the database: `mysql -u root -p < db.sql`
2. If your MySQL user/password differ from `root`/`root`, edit `db_config.php`.
3. Copy this folder into the web root (e.g. `htdocs/` in XAMPP or `/var/www/html/`), or run
   `php -S localhost:8000` inside the folder.
4. Open `http://localhost:8000/index.html` (or `http://localhost/49a_php_i_user_validation/`).

## Sample input / output
| Login name | Password | Result |
|------------|----------|--------|
| `student` | `pass123` | **Login Successful** – Welcome, Ravi Kumar (student)! |
| `admin` | `admin123` | **Login Successful** – Welcome, Administrator (admin)! |
| `student` | `wrong` | **Login Failed** – Invalid login name or password. |

## Notes
- A prepared statement is used, so SQL injection is not possible; output is escaped with `htmlspecialchars`.
- Passwords are stored as plain text to keep the lab simple. In a real application store
  `password_hash($pwd, PASSWORD_DEFAULT)` and check with `password_verify()`.
