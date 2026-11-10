/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Html contains small helper methods shared by all servlets: common page header/footer,
 * the "step" progress bar, HTML escaping and parameter reading.
 */

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

public final class Html {

    private Html() { }

    /** Writes the start of a page. wide = true gives a wider box (for tables). */
    public static void header(PrintWriter out, String title, boolean wide) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>" + esc(title) + "</title>");
        out.println("<link rel=\"stylesheet\" href=\"style.css\"></head><body>");
        out.println("<div class=\"box" + (wide ? " wide" : "") + "\">");
        out.println("<h1>Online Student Admission</h1>");
    }

    public static void footer(PrintWriter out) {
        out.println("</div></body></html>");
    }

    /** Progress bar: current = 1, 2 or 3 (4 = all done). */
    public static void steps(PrintWriter out, int current) {
        String[] names = {"1. Personal", "2. Academic", "3. Course &amp; Review"};
        out.print("<div class=\"steps\">");
        for (int i = 0; i < names.length; i++) {
            int step = i + 1;
            String cls = step == current ? "active" : (step < current ? "done" : "");
            out.print("<span class=\"" + cls + "\">" + names[i] + "</span>");
        }
        out.println("</div>");
    }

    /** Escapes text before writing it into HTML. */
    public static String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    /** Returns a trimmed request parameter, or "" if it is missing. */
    public static String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }

    /** "selected" attribute helper for <option>. */
    public static String sel(String current, String value) {
        return value.equals(current) ? " selected" : "";
    }
}
