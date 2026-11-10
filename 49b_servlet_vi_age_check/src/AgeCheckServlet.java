/*
 * Question 49 (b) vi) - Servlets
 * A web application that takes name and age from an HTML page. If the age is less than 18, it
 * should send a page with "Hello <name>, you are not authorized to visit this site" message,
 * where <name> should be replaced with the entered name. Otherwise it should send
 * "Welcome <name> to this site" message.
 *
 * AgeCheckServlet: receives name and age by POST and sends one of the two messages.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/agecheck")
public class AgeCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        String ageText = request.getParameter("age");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String safeName = HtmlUtil.escape(name == null ? "" : name.trim());
        int age;
        try {
            age = Integer.parseInt(ageText.trim());
        } catch (NumberFormatException | NullPointerException e) {
            page(out, "Invalid input", "red", "Please enter a valid age.");
            return;
        }

        if (age < 18) {
            page(out, "Not Authorized", "red",
                 "Hello " + safeName + ", you are not authorized to visit this site");
        } else {
            page(out, "Welcome", "green", "Welcome " + safeName + " to this site");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.html");
    }

    private void page(PrintWriter out, String title, String color, String message) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>" + title + "</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
        out.println("<h2 style=\"color:" + color + "\">" + message + "</h2>");
        out.println("<p><a href=\"index.html\">Back</a></p>");
        out.println("</body></html>");
    }
}
