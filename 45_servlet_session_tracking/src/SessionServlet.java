/*
 * Question 45: Develop a Java Servlet application with session tracking.
 *
 * SessionServlet demonstrates session tracking with javax.servlet.http.HttpSession:
 *   - stores the user's name in the session (POST from index.html)
 *   - keeps a visit counter that increases on every request in the same session
 *   - shows the session id, first-visit (creation) time, last access time,
 *     max inactive interval and whether the session is new
 * URLs are passed through response.encodeURL() so URL rewriting is used
 * automatically when the browser has cookies disabled.
 *
 * URL: http://localhost:8080/45_servlet_session_tracking/session
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

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String FMT = "dd-MM-yyyy HH:mm:ss";

    /** POST from index.html: remember the user's name in the session. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);     // create if absent
        String user = request.getParameter("user");
        if (user != null && !user.trim().isEmpty()) {
            session.setAttribute("user", user.trim());
        }
        showSession(request, response, session);
    }

    /** GET: show (or create) the session and count the visit. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        showSession(request, response, session);
    }

    private void showSession(HttpServletRequest request, HttpServletResponse response,
                             HttpSession session) throws IOException {
        // Visit counter stored as a session attribute
        Integer visits = (Integer) session.getAttribute("visits");
        visits = (visits == null) ? 1 : visits + 1;
        session.setAttribute("visits", visits);

        String user = (String) session.getAttribute("user");
        if (user == null) {
            user = "Guest";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(FMT);
        String created = sdf.format(new Date(session.getCreationTime()));
        String lastAccess = sdf.format(new Date(session.getLastAccessedTime()));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Session Info</title>");
        out.println("<style>body{font-family:Arial,sans-serif;padding:30px;}"
                + "table{border-collapse:collapse;}td,th{border:1px solid #999;padding:6px 12px;"
                + "text-align:left;}th{background:#2e6b2e;color:#fff;}</style></head><body>");

        out.println("<h2>" + (visits == 1 ? "Welcome" : "Welcome back") + ", "
                + escapeHtml(user) + "!</h2>");
        out.println("<table>");
        out.println("<tr><th>Property</th><th>Value</th></tr>");
        out.println(row("Session ID", session.getId()));
        out.println(row("New session?", String.valueOf(session.isNew())));
        out.println(row("Visit count (this session)", String.valueOf(visits)));
        out.println(row("First visit (creation time)", created));
        out.println(row("Previous access time", lastAccess));
        out.println(row("Current server time", sdf.format(new Date())));
        out.println(row("Max inactive interval", session.getMaxInactiveInterval() + " seconds"));
        out.println(row("Session id from cookie?",
                String.valueOf(request.isRequestedSessionIdFromCookie())));
        out.println(row("Session id from URL?",
                String.valueOf(request.isRequestedSessionIdFromURL())));
        out.println("</table>");

        // encodeURL() appends ;jsessionid=... when cookies are not available (URL rewriting)
        out.println("<p><a href=\"" + response.encodeURL("session") + "\">Refresh (visit again)</a>"
                + " | <a href=\"" + response.encodeURL("logout") + "\">Logout</a></p>");
        out.println("</body></html>");
    }

    private static String row(String key, String value) {
        return "<tr><td>" + key + "</td><td>" + escapeHtml(value) + "</td></tr>";
    }

    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
