/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Lists every admission saved in the MySQL table "admissions"  (URL: /admissions)
 * using a JDBC SELECT query.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admissions")
public class ListAdmissionsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String SELECT_SQL =
            "SELECT id, name, gender, dob, email, phone, qualification, percentage,"
            + " course, study_mode, hostel, applied_on FROM admissions ORDER BY id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.header(out, "All Admissions", true);
        out.println("<h2>All Admissions</h2>");

        SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFmt = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SELECT_SQL)) {

            out.println("<table><tr><th>App No</th><th>Name</th><th>Gender</th><th>DOB</th>"
                    + "<th>Email</th><th>Phone</th><th>Qualification</th><th>%</th>"
                    + "<th>Course</th><th>Mode</th><th>Hostel</th><th>Applied on</th></tr>");
            int count = 0;
            while (rs.next()) {
                count++;
                out.println("<tr>"
                        + td("ADM" + String.format("%04d", rs.getLong("id")))
                        + td(rs.getString("name"))
                        + td(rs.getString("gender"))
                        + td(dateFmt.format(rs.getDate("dob")))
                        + td(rs.getString("email"))
                        + td(rs.getString("phone"))
                        + td(rs.getString("qualification"))
                        + td(String.format("%.2f", rs.getDouble("percentage")))
                        + td(rs.getString("course"))
                        + td(rs.getString("study_mode"))
                        + td(rs.getBoolean("hostel") ? "Yes" : "No")
                        + td(timeFmt.format(rs.getTimestamp("applied_on")))
                        + "</tr>");
            }
            if (count == 0) {
                out.println("<tr><td colspan=\"12\">No admissions yet.</td></tr>");
            }
            out.println("</table>");
            out.println("<p class=\"small\">Total: " + count + " application(s)</p>");
        } catch (SQLException e) {
            log("Select failed", e);
            out.println("<p class=\"err\">Database error: " + Html.esc(e.getMessage()) + "</p>");
        }

        out.println("<a class=\"btn\" href=\"step1\">New Application</a> ");
        out.println("<a class=\"btn grey\" href=\"index.html\">Home</a>");
        Html.footer(out);
    }

    private static String td(String value) {
        return "<td>" + Html.esc(value) + "</td>";
    }
}
