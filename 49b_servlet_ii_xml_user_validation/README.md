# 49 (b) ii) User validation using Servlets + XML file

**Question:** Modify the above program (49 b i – user validation) to use an XML file instead of
database.

The login form is the same as in part (i), but the servlet checks the name and password against
`WEB-INF/users.xml`. The file is read with `getServletContext().getResourceAsStream()` and parsed
with the DOM parser (`DocumentBuilder`). Because it lives under `WEB-INF`, the browser cannot
download the XML file directly. No database is needed.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Login form (POSTs to `login`) |
| `WEB-INF/users.xml` | User data: `<user><username/><password/><fullname/></user>` |
| `src/XmlLoginServlet.java` | `@WebServlet("/login")` – parses `users.xml` and validates the user |
| `src/HtmlUtil.java` | Escapes text written into HTML |

## How to run (Tomcat 9)
1. Copy this folder into Tomcat as `tomcat/webapps/xmlvalidation/` (keep `WEB-INF/users.xml`).
2. Compile the servlets (run inside `tomcat/webapps/xmlvalidation/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
   No MySQL connector jar is needed for this part.
3. Start Tomcat and open <http://localhost:8080/xmlvalidation/>.

To add a user, add another `<user>` block to `WEB-INF/users.xml` (the file is re-read on every login).

## Sample input / output
| Login name | Password | Result |
|------------|----------|--------|
| `priya` | `priya@123` | **Login Successful** – "Welcome, Priya Sharma! You have logged in successfully." |
| `priya` | `1234` | **Login Failed** – "Invalid login name or password. Please try again." |
