/*
 * Question 49 (b) ix) - Servlets
 * (see AddCookieServlet.java for the full question text)
 *
 * ListCookiesServlet: called by the "List Cookies" button. Reads every cookie the browser
 * sent with request.getCookies() and shows them in a table.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listcookies")
public class ListCookiesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>All Cookies</title>");
        out.println("<style>body{font-family:Arial,sans-serif;text-align:center;margin-top:40px}"
                + "table{border-collapse:collapse;margin:auto}"
                + "th,td{border:1px solid #888;padding:6px 14px}th{background:#dde4f0}</style>");
        out.println("</head><body>");
        out.println("<h2>Cookies stored in the browser</h2>");

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            out.println("<p>No cookies found. <a href=\"index.html\">Add some cookies</a> first.</p>");
        } else {
            out.println("<table><tr><th>#</th><th>Name</th><th>Value</th></tr>");
            int i = 1;
            for (Cookie c : cookies) {
                out.println("<tr><td>" + (i++) + "</td><td>" + HtmlUtil.escape(c.getName())
                        + "</td><td>" + HtmlUtil.escape(decode(c.getValue())) + "</td></tr>");
            }
            out.println("</table>");
            out.println("<p>Total: " + cookies.length + " cookie(s)</p>");
        }
        out.println("<p><a href=\"index.html\">Back</a></p>");
        out.println("</body></html>");
    }

    /** Cookie values were URL-encoded by AddCookieServlet; other cookies are shown as they are. */
    private String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }
}
