/*
 * Question 49 (b) viii) - Servlets
 * Small helper that writes the common HTML page header/footer used by the servlets.
 */
import java.io.PrintWriter;

public class PageUtil {
    public static void start(PrintWriter out, String title) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>" + title + "</title>");
        out.println("<style>body{font-family:Arial,sans-serif;background:#f2f4f8}"
                + ".box{width:340px;margin:60px auto;padding:20px;background:#fff;"
                + "border:1px solid #ccc;border-radius:6px;text-align:center}"
                + "input[type=text]{width:100%;padding:6px;box-sizing:border-box}"
                + "input[type=submit]{margin-top:12px;padding:8px 20px}</style>");
        out.println("</head><body><div class=\"box\">");
    }

    public static void end(PrintWriter out) {
        out.println("</div></body></html>");
    }
}
