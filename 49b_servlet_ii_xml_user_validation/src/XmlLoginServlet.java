/*
 * Question 49 (b) ii) - Servlets
 * Modify the above program (49 b i: user validation web application) to use an XML file
 * instead of database.
 *
 * XmlLoginServlet: reads WEB-INF/users.xml with getServletContext().getResourceAsStream()
 * and a DOM parser, and checks the submitted login name and password against each <user>.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@WebServlet("/login")
public class XmlLoginServlet extends HttpServlet {

    private static final String USERS_FILE = "/WEB-INF/users.xml";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (username == null || password == null
                || username.trim().isEmpty() || password.isEmpty()) {
            page(out, "Login Failed", "red", "Please enter both login name and password.");
            return;
        }

        try {
            String fullName = findUser(username.trim(), password);
            if (fullName != null) {
                page(out, "Login Successful", "green",
                     "Welcome, " + HtmlUtil.escape(fullName)
                     + "! You have logged in successfully.");
            } else {
                page(out, "Login Failed", "red",
                     "Invalid login name or password. Please try again.");
            }
        } catch (Exception e) {
            page(out, "Error", "red", "Could not read users.xml: " + HtmlUtil.escape(e.getMessage()));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.html");
    }

    /** Returns the full name of the matching user, or null when no user matches. */
    private String findUser(String username, String password) throws Exception {
        try (InputStream in = getServletContext().getResourceAsStream(USERS_FILE)) {
            if (in == null) {
                throw new IOException(USERS_FILE + " not found");
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);

            NodeList users = doc.getElementsByTagName("user");
            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                String u = text(user, "username");
                String p = text(user, "password");
                if (username.equals(u) && password.equals(p)) {
                    return text(user, "fullname");
                }
            }
        }
        return null;
    }

    /** Text content of the first child element with the given tag name. */
    private String text(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        return list.getLength() > 0 ? list.item(0).getTextContent().trim() : "";
    }

    private void page(PrintWriter out, String title, String color, String message) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"en\"><head><meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("<title>" + title + "</title></head>");
        out.println("<body style=\"font-family:Arial,sans-serif;text-align:center;margin-top:60px\">");
        out.println("<h2 style=\"color:" + color + "\">" + title + "</h2>");
        out.println("<p>" + message + "</p>");
        out.println("<p><a href=\"index.html\">Back to login</a></p>");
        out.println("</body></html>");
    }
}
