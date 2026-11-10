# 49 (b) ix) List all cookies (Servlets)

**Question:** A web application that lists all cookies stored in the browser on clicking
"List Cookies" button. Add cookies if necessary.

## How it works
- `index.html` has a form to add a cookie (name, value, lifetime in minutes), an
  **Add Sample Cookies** button and the **List Cookies** button.
- `AddCookieServlet` (`/addcookie`) creates a `javax.servlet.http.Cookie`, sets `setMaxAge()` and
  sends it with `response.addCookie()`. Values are URL-encoded so spaces etc. are allowed.
- `ListCookiesServlet` (`/listcookies`) reads `request.getCookies()` and prints every cookie's name
  and value in a table (the browser only sends cookies belonging to this app's path).
  A `JSESSIONID` cookie may also appear if a session was created.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Add-cookie form, sample-cookie button, **List Cookies** button |
| `src/AddCookieServlet.java` | `@WebServlet("/addcookie")` – adds cookies |
| `src/ListCookiesServlet.java` | `@WebServlet("/listcookies")` – lists all cookies in a table |
| `src/HtmlUtil.java` | Escapes cookie names/values before writing them into HTML |

## How to run (Tomcat 9)
1. Copy this folder into Tomcat as `tomcat/webapps/cookies/`.
2. Compile (inside `tomcat/webapps/cookies/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
3. Start Tomcat and open <http://localhost:8080/cookies/>.

## Sample output
After clicking *Add Sample Cookies*, adding `city` = `Hyderabad`, then clicking *List Cookies*:

| # | Name | Value |
|---|------|-------|
| 1 | username | Ravi Kumar |
| 2 | language | English |
| 3 | theme | dark |
| 4 | city | Hyderabad |

Total: 4 cookie(s)
