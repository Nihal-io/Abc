/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Final submit  (URL: /submit, POST from page 3)
 *   - reads the course selection from page 3 and adds it to the Admission bean,
 *   - takes the data of ALL three pages from the HttpSession,
 *   - inserts one row into the MySQL table "admissions" with a JDBC PreparedStatement,
 *   - invalidates the session (the application is finished) and shows a confirmation
 *     with the generated application number.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/submit")
public class SubmitAdmissionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String INSERT_SQL =
            "INSERT INTO admissions (name, gender, dob, email, phone, address,"
            + " qualification, university, pass_year, percentage,"
            + " course, study_mode, hostel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /** A GET here (e.g. typing the URL) just goes back to page 3. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(resp.encodeRedirectURL("step3"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Admission a = session == null ? null : (Admission) session.getAttribute("admission");
        if (a == null || a.getCompletedStep() < 2) {
            resp.sendRedirect(resp.encodeRedirectURL("step1"));   // session expired / skipped pages
            return;
        }

        // Page 3 data
        a.setCourse(Html.param(req, "course"));
        a.setStudyMode(Html.param(req, "studyMode"));
        a.setHostel("yes".equals(req.getParameter("hostel")));

        if (!Arrays.asList(CourseSelectionServlet.COURSES).contains(a.getCourse())
                || !Arrays.asList(CourseSelectionServlet.MODES).contains(a.getStudyMode())) {
            CourseSelectionServlet.showPage(resp, a, "Please select a course and mode of study.");
            return;
        }
        if (!"yes".equals(req.getParameter("declare"))) {
            CourseSelectionServlet.showPage(resp, a, "Please accept the declaration.");
            return;
        }

        // Save all 3 pages of data in the database
        long appNo;
        try {
            appNo = insert(a);
        } catch (SQLException e) {
            log("Insert failed", e);
            // keep the session so the user can try again
            CourseSelectionServlet.showPage(resp, a, "Could not save the application: " + e.getMessage());
            return;
        }

        session.invalidate();      // application complete -> end the session

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.header(out, "Application Submitted", false);
        Html.steps(out, 4);
        out.println("<p class=\"ok\">Thank you, <b>" + Html.esc(a.getName()) + "</b>! Your application"
                + " has been saved.<br>Application number: <b>ADM" + String.format("%04d", appNo)
                + "</b></p>");
        out.println("<table>");
        out.println("<tr><th colspan=\"2\">Personal details (page 1)</th></tr>");
        CourseSelectionServlet.printSummaryRows(out, a, true);
        out.println("</table>");
        out.println("<a class=\"btn\" href=\"admissions\">View All Admissions</a> ");
        out.println("<a class=\"btn grey\" href=\"index.html\">Home</a>");
        Html.footer(out);
    }

    /** Inserts the application and returns the auto-generated id. */
    private static long insert(Admission a) throws SQLException {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getName());
            ps.setString(2, a.getGender());
            ps.setDate(3, java.sql.Date.valueOf(a.getDob()));
            ps.setString(4, a.getEmail());
            ps.setString(5, a.getPhone());
            ps.setString(6, a.getAddress());
            ps.setString(7, a.getQualification());
            ps.setString(8, a.getUniversity());
            ps.setInt(9, a.getPassYear());
            ps.setDouble(10, a.getPercentage());
            ps.setString(11, a.getCourse());
            ps.setString(12, a.getStudyMode());
            ps.setBoolean(13, a.isHostel());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getLong(1) : 0;
            }
        }
    }
}
