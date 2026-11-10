/*
 * Question 49 (b) iv) - Servlets
 * A simple calculator application that takes two numbers and an operator (+, -, *, /, %) from an
 * HTML page and returns the result page with the operation performed on the operands.
 *
 * CalculatorServlet: validates both numbers, performs the chosen operation and sends back a
 * result page. Non-numeric input and division / modulus by zero give an error message.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculate")
public class CalculatorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String s1 = request.getParameter("num1");
        String s2 = request.getParameter("num2");
        String op = request.getParameter("op");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        double a, b;
        try {
            a = Double.parseDouble(s1.trim());
            b = Double.parseDouble(s2.trim());
            if (Double.isNaN(a) || Double.isInfinite(a) || Double.isNaN(b) || Double.isInfinite(b)) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException | NullPointerException e) {
            page(out, "Error", "Please enter two valid numbers (you entered \""
                    + HtmlUtil.escape(s1) + "\" and \"" + HtmlUtil.escape(s2) + "\").", true);
            return;
        }

        if (op == null) op = "";
        double result;
        switch (op) {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "*": result = a * b; break;
            case "/":
                if (b == 0) { page(out, "Error", "Division by zero is not allowed.", true); return; }
                result = a / b;
                break;
            case "%":
                if (b == 0) { page(out, "Error", "Modulus by zero is not allowed.", true); return; }
                result = a % b;
                break;
            default:
                page(out, "Error", "Unknown operator \"" + HtmlUtil.escape(op) + "\".", true);
                return;
        }

        String expr = format(a) + " " + HtmlUtil.escape(op) + " " + format(b) + " = <b>" + format(result) + "</b>";
        page(out, "Result", expr, false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.html");
    }

    /** Shows 12 instead of 12.0, otherwise up to 6 decimal places. */
    private String format(double d) {
        if (d == Math.rint(d) && Math.abs(d) < 1e15) {
            return String.valueOf((long) d);
        }
        String s = String.format(java.util.Locale.ROOT, "%.6f", d);
        s = s.replaceAll("0+$", "");
        return s.endsWith(".") ? s.substring(0, s.length() - 1) : s;
    }

    private void page(PrintWriter out, String title, String message, boolean error) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Calculator - " + title + "</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
        out.println("<h2 style=\"color:" + (error ? "red" : "green") + "\">" + title + "</h2>");
        out.println("<p style=\"font-size:20px\">" + message + "</p>");
        out.println("<p><a href=\"index.html\">Back to calculator</a></p>");
        out.println("</body></html>");
    }
}
