# 49 (b) iii) User validation using Servlets + AJAX

**Question:** Modify the above program (49 b i – user validation) using AJAX to show the result
on the same page below the submit button.

`index.html` sends the login name and password with an `XMLHttpRequest` POST to the servlet
`ajaxlogin`. The servlet checks them in the MySQL `users` table (JDBC `PreparedStatement`) and
returns only a short **plain-text** message. JavaScript puts that text in the `<div id="result">`
below the Login button – the page is never reloaded. HTTP status 200 = success (green text),
401 = failure (red text).

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login form + AJAX script + result `<div>` |
| `src/AjaxLoginServlet.java` | `@WebServlet("/ajaxlogin")` – checks the user, replies with plain text |
| `src/DBUtil.java` | JDBC connection details (`labdb`, `root` / `root` – change if needed) |
| `db.sql` | Creates database `labdb`, table `users` and sample users (same as part i) |

The reply is plain text inserted with `textContent`, so no HTML escaping helper is needed here.

## How to run (Tomcat 9)
1. Create the database: `mysql -u root -p < db.sql` (skip if already done for part i).
2. Copy this folder into Tomcat as `tomcat/webapps/ajaxvalidation/`.
3. Put MySQL Connector/J (`mysql-connector-j-x.y.z.jar`) in
   `tomcat/webapps/ajaxvalidation/WEB-INF/lib/`.
4. Compile (inside `tomcat/webapps/ajaxvalidation/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
5. Start Tomcat and open <http://localhost:8080/ajaxvalidation/>.

## Sample input / output (shown below the button, same page)
| Login name | Password | Text shown |
|------------|----------|------------|
| `admin` | `admin123` | <span style="color:green">Login successful. Welcome, Administrator!</span> |
| `admin` | `xyz` | <span style="color:red">Login failed: invalid login name or password.</span> |
