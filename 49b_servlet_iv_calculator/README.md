# 49 (b) iv) Simple calculator using Servlets

**Question:** A simple calculator application that takes two numbers and an operator
(+, -, *, /, %) from an HTML page and returns the result page with the operation performed on the
operands.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Form with two number fields and an operator drop-down (POSTs to `calculate`) |
| `src/CalculatorServlet.java` | `@WebServlet("/calculate")` – performs the operation and returns the result page |
| `src/HtmlUtil.java` | Escapes user input that is echoed back in error messages |

Error handling: non-numeric input, an unknown operator, and division / modulus by zero each show
an error page instead of a wrong answer or an exception. Decimal numbers are allowed.

## How to run (Tomcat 9)
1. Copy this folder into Tomcat as `tomcat/webapps/calculator/`.
2. Compile (inside `tomcat/webapps/calculator/`):
   ```
   mkdir -p WEB-INF/classes
   javac -cp <tomcat>/lib/servlet-api.jar -d WEB-INF/classes src/*.java
   ```
3. Start Tomcat and open <http://localhost:8080/calculator/>.

## Sample input / output
| First | Op | Second | Result page |
|-------|----|--------|-------------|
| 12 | + | 8 | `12 + 8 = 20` |
| 7 | / | 2 | `7 / 2 = 3.5` |
| 17 | % | 5 | `17 % 5 = 2` |
| 10 | / | 0 | **Error** – Division by zero is not allowed. |
| abc | * | 3 | **Error** – Please enter two valid numbers (you entered "abc" and "3"). |
