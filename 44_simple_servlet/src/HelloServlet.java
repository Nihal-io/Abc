/*
 * Question 44: Develop a simple Java Servlet application.
 *
 * HelloServlet reads the "name" parameter sent from index.html and prints a
 * greeting together with the current server time. It handles both GET and POST
 * requests and logs the servlet life-cycle methods init(), service() and destroy().
 *
 * URL: http://localhost:8080/44_simple_servlet/hello
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Number of requests served since init() - shows the servlet is a single instance. */
    private int requestCount;

    /** Life-cycle step 1: called once when the container loads the servlet. */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        requestCount = 0;
        System.out.println("[HelloServlet] init() called - servlet loaded at " + now());
        log("HelloServlet initialised");
    }

    /** Handles GET requests (form with method="get" or a plain URL). */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[HelloServlet] doGet() called");
        sendGreeting(request, response, "GET");
    }

    /** Handles POST requests (form with method="post"). */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("[HelloServlet] doPost() called");
        sendGreeting(request, response, "POST");
    }

    /** Life-cycle step 3: called once when the application is stopped/undeployed. */
    @Override
    public void destroy() {
        System.out.println("[HelloServlet] destroy() called - served " + requestCount
                + " request(s). Unloaded at " + now());
        log("HelloServlet destroyed");
    }

    /* ---------------- helper methods ---------------- */

    private void sendGreeting(HttpServletRequest request, HttpServletResponse response,
                              String method) throws IOException {
        requestCount++;

        String name = request.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            name = "Guest";
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>Greeting</title>");
        out.println("<style>body{font-family:Arial,sans-serif;text-align:center;padding:40px;}"
                + "h1{color:#1a4d80;}</style></head><body>");
        out.println("<h1>Hello, " + escapeHtml(name.trim()) + "!</h1>");
        out.println("<p>Welcome to Java Servlets.</p>");
        out.println("<p>Server time: <b>" + now() + "</b></p>");
        out.println("<p>Request method: <b>" + method + "</b></p>");
        out.println("<p>Requests served by this servlet instance: <b>" + requestCount + "</b></p>");
        out.println("<p><a href=\"index.html\">Back</a></p>");
        out.println("</body></html>");
    }

    private static String now() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

    /** Minimal HTML escaping so user input cannot inject markup. */
    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
