/*
 * Question 49 (b) vii) - Servlets
 * (see CheckAgeServlet.java for the full question text)
 *
 * WelcomeServlet: reached by RequestDispatcher.forward() from CheckAgeServlet when age >= 18.
 * Shows "Welcome <name> to this site".
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = (String) request.getAttribute("name");
        if (name == null) {                    // opened directly, not via /check
            response.sendRedirect("index.html");
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Welcome</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
        out.println("<h2 style=\"color:green\">Welcome " + HtmlUtil.escape(name) + " to this site</h2>");
        out.println("<p><a href=\"index.html\">Back</a></p>");
        out.println("</body></html>");
    }
}
