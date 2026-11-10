# 49 (b) vi) Age check using Servlets (POST)

**Question:** A web application that takes name and age from an HTML page. If the age is less
than 18, it should send a page with "Hello <name>, you are not authorized to visit this site"
message, where <name> should be replaced with the entered name. Otherwise it should send
"Welcome <name> to this site" message.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Name + age form (POSTs to `agecheck`) |
| `src/AgeCheckServlet.java` | `@WebServlet("/agecheck")` – `doPost` compares age with 18 and writes the message |
| `src/HtmlUtil.java` | Escapes the name before writing it into HTML |

(Part vii solves the same question a second way – with GET and `RequestDispatcher`.)

## How to run (Tomcat 9)
1. Copy this folder into Tomcat as `tomcat/webapps/agecheck/`.
2. Compile (inside `tomcat/webapps/agecheck/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
3. Start Tomcat and open <http://localhost:8080/agecheck/>.

## Sample input / output
| Name | Age | Page shows |
|------|-----|-----------|
| Rahul | 15 | Hello Rahul, you are not authorized to visit this site |
| Meena | 21 | Welcome Meena to this site |
