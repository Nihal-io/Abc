/*
 * Question 49 (b) ix) - Servlets
 * A web application that lists all cookies stored in the browser on clicking "List Cookies"
 * button. Add cookies if necessary.
 *
 * AddCookieServlet: adds a cookie (name, value, lifetime in minutes) to the browser with
 * response.addCookie(). With sample=yes it adds three sample cookies instead.
 * Values are URL-encoded so spaces and special characters are allowed.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addcookie")
public class AddCookieServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String message;

        if ("yes".equals(request.getParameter("sample"))) {
            add(response, "username", "Ravi Kumar", 60);
            add(response, "language", "English", 60);
            add(response, "theme", "dark", 60);
            message = "Added sample cookies: username, language, theme (valid for 60 minutes).";
        } else {
            String name = request.getParameter("cname");
            String value = request.getParameter("cvalue");
            int minutes = 60;
            try {
                minutes = Integer.parseInt(request.getParameter("minutes"));
            } catch (NumberFormatException e) {
                // keep the default of 60 minutes
            }
            if (minutes < 1) minutes = 1;

            if (name == null || !name.matches("[A-Za-z0-9_\\-]+")) {
                message = "Invalid cookie name. Use letters, digits, _ and - only.";
            } else if (name.equalsIgnoreCase("JSESSIONID")) {
                message = "JSESSIONID is reserved by the server; choose another name.";
            } else {
                add(response, name, value == null ? "" : value, minutes);
                message = "Cookie <b>" + HtmlUtil.escape(name) + "</b> = <b>"
                        + HtmlUtil.escape(value) + "</b> added (valid for " + minutes + " minutes).";
            }
        }

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Cookie added</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
        out.println("<p>" + message + "</p>");
        out.println("<form action=\"listcookies\" method=\"get\">"
                + "<input type=\"submit\" value=\"List Cookies\"></form>");
        out.println("<p><a href=\"index.html\">Add another cookie</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.html");
    }

    private void add(HttpServletResponse response, String name, String value, int minutes)
            throws IOException {
        Cookie c = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
        c.setMaxAge(minutes * 60);        // seconds; without this it is deleted when the browser closes
        response.addCookie(c);
    }
}
