/*
 * Question 49 (b) v) - Servlets
 * A web application takes a name as input and on submit it shows a hello<name> page where <name>
 * is taken from the request. It shows the start time at the right top corner of the page and
 * provides the logout button. On clicking this button, it should show a logout page with
 * Thank You<name> message with the duration of usage. (Use session to store name and time).
 *
 * HelloServlet: stores the name and the start time in the HttpSession and shows the
 * "Hello <name>" page with the start time in the top-right corner and a Logout button.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    /** Form submit: start a new session with the name and the current time. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            response.sendRedirect("index.html");
            return;
        }

        HttpSession old = request.getSession(false);
        if (old != null) {
            old.invalidate();                 // a new name starts a fresh session
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("name", name.trim());
        session.setAttribute("startTime", System.currentTimeMillis());

        showHello(response, session);
    }

    /** Refreshing /hello re-shows the page while the session is still alive. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("name") == null) {
            response.sendRedirect("index.html");
            return;
        }
        showHello(response, session);
    }

    private void showHello(HttpServletResponse response, HttpSession session) throws IOException {
        String name = (String) session.getAttribute("name");
        long start = (Long) session.getAttribute("startTime");
        String startText = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(new Date(start));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Hello " + HtmlUtil.escape(name) + "</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;margin:0;padding:20px}");
        out.println(".start{position:absolute;top:10px;right:15px;color:#555;font-size:14px}");
        out.println("h1{margin-top:60px;text-align:center}");
        out.println("form{text-align:center}");
        out.println("</style></head><body>");
        out.println("<div class=\"start\">Start time: " + startText + "</div>");
        out.println("<h1>Hello " + HtmlUtil.escape(name) + "</h1>");
        out.println("<form action=\"logout\" method=\"post\">");
        out.println("<input type=\"submit\" value=\"Logout\">");
        out.println("</form>");
        out.println("</body></html>");
    }
}
