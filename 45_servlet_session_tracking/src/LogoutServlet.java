/*
 * Question 45: Develop a Java Servlet application with session tracking.
 *
 * LogoutServlet ends the session with HttpSession.invalidate(). After logout,
 * visiting /session again starts a brand new session (new id, counter = 1).
 *
 * URL: http://localhost:8080/45_servlet_session_tracking/logout
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

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);   // do NOT create a new one
        String message;
        if (session != null) {
            Object visits = session.getAttribute("visits");
            String oldId = session.getId();
            session.invalidate();                            // destroy the session
            message = "Session " + oldId + " invalidated after " + visits + " visit(s).";
        } else {
            message = "No active session to invalidate.";
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Logged out</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;padding:30px;\">");
        out.println("<h2>You have been logged out</h2>");
        out.println("<p>" + message + "</p>");
        out.println("<p><a href=\"index.html\">Start a new session</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
