# 49 (b) vii) Age check using Servlets (GET + RequestDispatcher)

**Question:** A web application that takes name and age from an HTML page. If the age is less
than 18, it should send a page with "Hello <name>, you are not authorized to visit this site"
message, where <name> should be replaced with the entered name. Otherwise it should send
"Welcome <name> to this site" message.

> **Note:** In the question paper, part **vii** repeats the text of part **vi** word for word.
> Part vi is answered with a single servlet using POST; this part solves the same requirement a
> different way so that both answers are useful.

## Differences from part vi
- The form uses **GET**, so name and age travel in the query string
  (e.g. `check?name=Ravi&age=20`) and are read in `doGet`.
- The age is **validated** on the server: it must be a non-negative whole number; otherwise an
  "Invalid input" page is shown.
- `CheckAgeServlet` does not write the answer itself. It stores `name` and `age` as request
  attributes and **forwards** with `RequestDispatcher` to one of two result servlets:
  `NotAuthorizedServlet` (age < 18) or `WelcomeServlet` (age ≥ 18). The browser's URL stays
  `/check?...` because a forward happens inside the server.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Name + age form (GET to `check`) |
| `src/CheckAgeServlet.java` | `@WebServlet("/check")` – validates input and forwards |
| `src/WelcomeServlet.java` | `@WebServlet("/welcome")` – "Welcome &lt;name&gt; to this site" |
| `src/NotAuthorizedServlet.java` | `@WebServlet("/notauthorized")` – "Hello &lt;name&gt;, you are not authorized…" |
| `src/HtmlUtil.java` | Escapes the name before writing it into HTML |

## How to run (Tomcat 9)
1. Copy this folder into Tomcat as `tomcat/webapps/agecheckget/`.
2. Compile (inside `tomcat/webapps/agecheckget/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
3. Start Tomcat and open <http://localhost:8080/agecheckget/>.

## Sample input / output
| URL after submit | Page shows |
|------------------|-----------|
| `check?name=Rahul&age=15` | Hello Rahul, you are not authorized to visit this site |
| `check?name=Meena&age=21` | Welcome Meena to this site |
| `check?name=Meena&age=-4` | Invalid input – Age must be a non-negative whole number (0 - 999). |
| `check?name=Meena&age=abc` | Invalid input – Age must be a non-negative whole number (0 - 999). |
