/*
 * Question 49 (b) viii) - Servlets
 * A web application for implementation: The user is first served a login page which takes
 * user's name and password. After submitting the details the server checks these values against
 * the data from a database and takes the following decisions. If name and password matches
 * serves a welcome page with user's full name. If name and password doesn't match, then serves
 * "password mismatch" page. If name is not found in the database, serves a registration page,
 * where user's full name is asked and on submitting the full name, it stores the login name,
 * password and full name in the database (hint: use session for storing data, submitted login
 * name and password).
 *
 * LoginServlet: looks the login name up in the "users" table and decides:
 *   - name found, password matches  -> welcome page with full name
 *   - name found, password differs  -> "password mismatch" page
 *   - name not found                -> save name + password in the session and redirect
 *                                      to the registration page (RegisterServlet)
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null
                || username.trim().isEmpty() || password.isEmpty()) {
            response.sendRedirect("index.html");
            return;
        }
        username = username.trim();

        response.setContentType("text/html;charset=UTF-8");
        String sql = "SELECT password, fullname FROM users WHERE username = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    // Name not found: remember the submitted values and ask for the full name.
                    HttpSession session = request.getSession(true);
                    session.setAttribute("regUsername", username);
                    session.setAttribute("regPassword", password);
                    response.sendRedirect("register");
                    return;
                }

                PrintWriter out = response.getWriter();
                if (password.equals(rs.getString("password"))) {
                    PageUtil.start(out, "Welcome");
                    out.println("<h2 style=\"color:green\">Welcome, "
                            + HtmlUtil.escape(rs.getString("fullname")) + "!</h2>");
                    out.println("<p>You have logged in as <b>" + HtmlUtil.escape(username) + "</b>.</p>");
                } else {
                    PageUtil.start(out, "Password mismatch");
                    out.println("<h2 style=\"color:red\">Password mismatch</h2>");
                    out.println("<p>The password you entered for <b>" + HtmlUtil.escape(username)
                            + "</b> is incorrect.</p>");
                }
                out.println("<p><a href=\"index.html\">Back to login</a></p>");
                PageUtil.end(out);
            }
        } catch (SQLException e) {
            PrintWriter out = response.getWriter();
            PageUtil.start(out, "Error");
            out.println("<h2 style=\"color:red\">Database error</h2>");
            out.println("<p>" + HtmlUtil.escape(e.getMessage()) + "</p>");
            PageUtil.end(out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.html");
    }
}
