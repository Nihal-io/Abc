# 49 (b) v) Hello page with session start time and logout (Servlets)

**Question:** A web application takes a name as input and on submit it shows a hello<name> page
where <name> is taken from the request. It shows the start time at the right top corner of the
page and provides the logout button. On clicking this button, it should show a logout page with
Thank You<name> message with the duration of usage. (Use session to store name and time).

## How it works
1. `index.html` posts the name to `HelloServlet` (`/hello`).
2. `HelloServlet` stores `name` and `startTime` (milliseconds) in the `HttpSession` and shows
   **Hello &lt;name&gt;** with the start time fixed in the top-right corner and a **Logout** button.
3. `LogoutServlet` (`/logout`) reads both values from the session, calculates
   *now − startTime*, shows **Thank You &lt;name&gt;** with the duration and invalidates the session.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Name input form |
| `src/HelloServlet.java` | `@WebServlet("/hello")` – creates the session, shows the hello page |
| `src/LogoutServlet.java` | `@WebServlet("/logout")` – shows thank-you page with duration, ends session |
| `src/HtmlUtil.java` | Escapes the name before writing it into HTML |

## How to run (Tomcat 9)
1. Copy this folder into Tomcat as `tomcat/webapps/sessionhello/`.
2. Compile (inside `tomcat/webapps/sessionhello/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
3. Start Tomcat and open <http://localhost:8080/sessionhello/>.

## Sample output
Input: `Anita`

Hello page:
```
                                         Start time: 10-11-2026 10:15:02 AM
                      Hello Anita
                       [ Logout ]
```
After clicking Logout about 2½ minutes later:
```
Thank You Anita
You used this site for 0 hour(s) 2 minute(s) 31 second(s).
```
