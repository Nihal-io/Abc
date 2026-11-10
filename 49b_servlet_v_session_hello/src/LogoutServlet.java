/*
 * Question 49 (b) v) - Servlets
 * (see HelloServlet.java for the full question text)
 *
 * LogoutServlet: reads the name and start time from the session, works out how long the
 * user stayed, shows "Thank You <name>" with the duration and then ends the session.
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("name") == null) {
            response.sendRedirect("index.html");    // nobody is logged in
            return;
        }

        String name = (String) session.getAttribute("name");
        long start = (Long) session.getAttribute("startTime");
        long seconds = (System.currentTimeMillis() - start) / 1000;
        session.invalidate();

        String duration = (seconds / 3600) + " hour(s) " + ((seconds % 3600) / 60) + " minute(s) "
                + (seconds % 60) + " second(s)";

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Logged out</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
        out.println("<h1>Thank You " + HtmlUtil.escape(name) + "</h1>");
        out.println("<p>You used this site for <b>" + duration + "</b>.</p>");
        out.println("<p><a href=\"index.html\">Start again</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
