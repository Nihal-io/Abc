# 49 (b) viii) Login with password-mismatch and registration (Servlets + JDBC + Session)

**Question:** The user is first served a login page which takes user's name and password. After
submitting the details the server checks these values against the data from a database and takes
the following decisions. If name and password matches serves a welcome page with user's full name.
If name and password doesn't match, then serves "password mismatch" page. If name is not found in
the database, serves a registration page, where user's full name is asked and on submitting the
full name, it stores the login name, password and full name in the database (hint: use session for
storing data, submitted login name and password).

## Flow
```
index.html --POST--> /login (LoginServlet)
                        |-- name found, password OK    --> Welcome <full name>
                        |-- name found, password wrong --> Password mismatch
                        '-- name not found --> session: regUsername, regPassword
                                               redirect --> /register (GET: full-name form)
                                                              --POST--> INSERT into users
                                                                        --> Welcome <full name>
```

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login page |
| `src/LoginServlet.java` | `@WebServlet("/login")` – the three-way decision |
| `src/RegisterServlet.java` | `@WebServlet("/register")` – registration form (GET) and INSERT (POST), using the session values |
| `src/DBUtil.java` | JDBC connection details (`labdb`, `root` / `root` – change if needed) |
| `src/HtmlUtil.java` | Escapes text written into HTML |
| `src/PageUtil.java` | Common HTML page start/end |
| `db.sql` | Creates database `labdb`, table `users` and sample users |

All SQL uses `PreparedStatement`. `username` is `UNIQUE`, so a duplicate registration shows a
friendly message.

## How to run (Tomcat 9)
1. Create the database: `mysql -u root -p < db.sql`
2. Copy this folder into Tomcat as `tomcat/webapps/loginregister/`.
3. Put MySQL Connector/J (`mysql-connector-j-x.y.z.jar`) in
   `tomcat/webapps/loginregister/WEB-INF/lib/`.
4. Compile (inside `tomcat/webapps/loginregister/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
5. Start Tomcat and open <http://localhost:8080/loginregister/>.

## Sample input / output
| Login name | Password | Result |
|------------|----------|--------|
| `ravi` | `ravi@123` | Welcome, Ravi Kumar! |
| `ravi` | `abc` | Password mismatch |
| `kiran` | `kiran@1` | Registration page → enter *Kiran Reddy* → "Registration successful. Welcome, Kiran Reddy!" (a later login with `kiran` / `kiran@1` shows *Welcome, Kiran Reddy!*) |
