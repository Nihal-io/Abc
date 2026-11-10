/*
 * Question 49 (b) i) - Servlets
 * A user validation web application, where the user submits the login name and password to
 * the server. The name and password are checked against the data already available in
 * Database and if the data matches, a successful login page is returned. Otherwise a failure
 * message is shown to the user.
 *
 * LoginServlet: reads username/password from the form and checks them against the
 * "users" table using a JDBC PreparedStatement.
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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

        String sql = "SELECT fullname FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    page(out, "Login Successful", "green",
                         "Welcome, " + HtmlUtil.escape(rs.getString("fullname"))
                         + "! You have logged in successfully.");
                } else {
                    page(out, "Login Failed", "red",
                         "Invalid login name or password. Please try again.");
                }
            }
        } catch (SQLException e) {
            page(out, "Error", "red", "Database error: " + HtmlUtil.escape(e.getMessage()));
        }
    }

    /** Opening /login directly in the browser just sends the user back to the form. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.html");
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
