/*
 * Question 49 (b) viii) - Servlets
 * (see LoginServlet.java for the full question text)
 *
 * RegisterServlet:
 *   doGet  - shows the registration page asking for the full name. The login name and
 *            password were stored in the session by LoginServlet.
 *   doPost - reads the full name from the form and the login name + password from the
 *            session, INSERTs them into the "users" table and shows the welcome page.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("regUsername") == null) {
            response.sendRedirect("index.html");   // must come from the login page
            return;
        }
        String username = (String) session.getAttribute("regUsername");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        PageUtil.start(out, "Registration");
        out.println("<h2>New User Registration</h2>");
        out.println("<p>The login name <b>" + HtmlUtil.escape(username)
                + "</b> was not found. Enter your full name to register.</p>");
        out.println("<form action=\"register\" method=\"post\">");
        out.println("<input type=\"text\" name=\"fullname\" placeholder=\"Full name\" required>");
        out.println("<input type=\"submit\" value=\"Register\">");
        out.println("</form>");
        out.println("<p><a href=\"index.html\">Cancel</a></p>");
        PageUtil.end(out);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("regUsername") == null) {
            response.sendRedirect("index.html");
            return;
        }
        String fullname = request.getParameter("fullname");
        if (fullname == null || fullname.trim().isEmpty()) {
            response.sendRedirect("register");     // ask again
            return;
        }
        fullname = fullname.trim();
        String username = (String) session.getAttribute("regUsername");
        String password = (String) session.getAttribute("regPassword");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String sql = "INSERT INTO users (username, password, fullname) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, fullname);
            ps.executeUpdate();

            // Registration data is no longer needed in the session.
            session.removeAttribute("regUsername");
            session.removeAttribute("regPassword");

            PageUtil.start(out, "Welcome");
            out.println("<h2 style=\"color:green\">Registration successful</h2>");
            out.println("<p>Welcome, <b>" + HtmlUtil.escape(fullname) + "</b>! Your login name is <b>"
                    + HtmlUtil.escape(username) + "</b>.</p>");
            out.println("<p><a href=\"index.html\">Go to login</a></p>");
            PageUtil.end(out);
        } catch (SQLIntegrityConstraintViolationException e) {
            PageUtil.start(out, "Registration failed");
            out.println("<h2 style=\"color:red\">Login name already taken</h2>");
            out.println("<p>Someone registered <b>" + HtmlUtil.escape(username)
                    + "</b> in the meantime. Please choose another login name.</p>");
            out.println("<p><a href=\"index.html\">Back to login</a></p>");
            PageUtil.end(out);
        } catch (SQLException e) {
            PageUtil.start(out, "Error");
            out.println("<h2 style=\"color:red\">Database error</h2>");
            out.println("<p>" + HtmlUtil.escape(e.getMessage()) + "</p>");
            PageUtil.end(out);
        }
    }
}
