# 49 (b) i) User validation using Servlets + JDBC

**Question:** A user validation web application, where the user submits the login name and
password to the server. The name and password are checked against the data already available in
Database and if the data matches, a successful login page is returned. Otherwise a failure message
is shown to the user.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login form (POSTs to `login`) |
| `src/LoginServlet.java` | `@WebServlet("/login")` – checks name/password with a JDBC `PreparedStatement` |
| `src/DBUtil.java` | JDBC connection details (`labdb`, `root` / `root` – change if needed) |
| `src/HtmlUtil.java` | Escapes text written into HTML |
| `db.sql` | Creates database `labdb`, table `users` and sample users |

## How to run (Tomcat 9)
1. Create the database: `mysql -u root -p < db.sql`
2. Copy this folder into Tomcat as `tomcat/webapps/uservalidation/`.
3. Download MySQL Connector/J (`mysql-connector-j-x.y.z.jar`) and put it in
   `tomcat/webapps/uservalidation/WEB-INF/lib/`.
4. Compile the servlets (run inside `tomcat/webapps/uservalidation/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
   (On Windows use `-cp "C:\tomcat\lib\servlet-api.jar"`.)
5. Start Tomcat and open <http://localhost:8080/uservalidation/>.

## Sample input / output
| Login name | Password | Result |
|------------|----------|--------|
| `ravi` | `ravi@123` | **Login Successful** – "Welcome, Ravi Kumar! You have logged in successfully." |
| `ravi` | `wrong` | **Login Failed** – "Invalid login name or password. Please try again." |
