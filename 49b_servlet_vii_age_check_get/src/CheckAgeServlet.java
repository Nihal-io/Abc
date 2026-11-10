/*
 * Question 49 (b) vii) - Servlets
 * A web application that takes name and age from an HTML page. If the age is less than 18, it
 * should send a page with "Hello <name>, you are not authorized to visit this site" message,
 * where <name> should be replaced with the entered name. Otherwise it should send
 * "Welcome <name> to this site" message.
 * (Same text as part vi in the question paper; solved here a different way.)
 *
 * CheckAgeServlet: doGet reads name and age from the query string, validates them
 * (age must be a non-negative whole number) and FORWARDS the request with RequestDispatcher
 * to NotAuthorizedServlet (age < 18) or WelcomeServlet (age >= 18).
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/check")
public class CheckAgeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String ageText = request.getParameter("age");

        // ---- validation ----
        String error = null;
        int age = -1;
        if (name == null || name.trim().isEmpty()) {
            error = "Name is required.";
        } else if (ageText == null || !ageText.trim().matches("\\d{1,3}")) {
            error = "Age must be a non-negative whole number (0 - 999).";
        } else {
            age = Integer.parseInt(ageText.trim());
        }

        if (error != null) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("<title>Invalid input</title></head>");
            out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
            out.println("<h2 style=\"color:red\">Invalid input</h2>");
            out.println("<p>" + error + "</p>");
            out.println("<p><a href=\"index.html\">Back</a></p>");
            out.println("</body></html>");
            return;
        }

        // ---- pass data to the result servlet and forward ----
        request.setAttribute("name", name.trim());
        request.setAttribute("age", age);
        String target = (age < 18) ? "/notauthorized" : "/welcome";
        RequestDispatcher rd = request.getRequestDispatcher(target);
        rd.forward(request, response);
    }
}
