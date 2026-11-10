/*
 * Question 49 (b) iii) - Servlets
 * Modify the above program (49 b i: user validation web application) using AJAX to show the
 * result on the same page below the submit button.
 *
 * AjaxLoginServlet: called by XMLHttpRequest from index.html. Checks the login name and
 * password in the "users" table and returns a short plain-text message (not a full page).
 * HTTP status 200 means success, 401 means invalid user (the page uses it to pick a colour).
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

@WebServlet("/ajaxlogin")
public class AjaxLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Plain text: the browser inserts it with textContent, so no HTML escaping is needed.
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (username == null || password == null
                || username.trim().isEmpty() || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Please enter both login name and password.");
            return;
        }

        String sql = "SELECT fullname FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    out.print("Login successful. Welcome, " + rs.getString("fullname") + "!");
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.print("Login failed: invalid login name or password.");
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("Database error: " + e.getMessage());
        }
    }
}
