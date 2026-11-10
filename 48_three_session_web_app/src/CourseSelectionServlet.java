/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Page 3 of 3 - Course selection & review  (URL: /step3)
 *   GET : shows everything entered on pages 1 and 2 (read back from the HttpSession) so the
 *         user can review it, plus the course-selection form. The form is submitted to
 *         /submit (SubmitAdmissionServlet) which saves the whole application in MySQL.
 *         Only allowed after pages 1 and 2 are complete.
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/step3")
public class CourseSelectionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    static final String[] COURSES = {"MCA", "MBA", "M.Sc Computer Science", "M.Sc Data Science", "M.Tech CSE"};
    static final String[] MODES = {"Regular", "Part-time"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Admission a = session == null ? null : (Admission) session.getAttribute("admission");
        if (a == null || a.getCompletedStep() < 2) {
            resp.sendRedirect(resp.encodeRedirectURL(a == null || a.getCompletedStep() < 1 ? "step1" : "step2"));
            return;
        }
        showPage(resp, a, null);
    }

    /** Also used by SubmitAdmissionServlet to redisplay page 3 with an error message. */
    static void showPage(HttpServletResponse resp, Admission a, String error) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.header(out, "Step 3 - Course Selection & Review", false);
        Html.steps(out, 3);

        out.println("<h2>Review your details</h2>");
        out.println("<table>");
        out.println("<tr><th colspan=\"2\">Personal details (page 1)</th></tr>");
        printSummaryRows(out, a, false);
        out.println("</table>");
        out.println("<p class=\"small\"><a href=\"" + resp.encodeURL("step1") + "\">Edit personal details</a> | "
                + "<a href=\"" + resp.encodeURL("step2") + "\">Edit academic details</a></p>");

        out.println("<h2>Course Selection</h2>");
        if (error != null) {
            out.println("<p class=\"err\">" + Html.esc(error) + "</p>");
        }
        out.println("<form action=\"" + resp.encodeURL("submit") + "\" method=\"post\">");
        out.println("<label for=\"course\">Course applied for</label>");
        out.println("<select id=\"course\" name=\"course\" required>");
        out.println("<option value=\"\">-- select --</option>");
        for (String c : COURSES) {
            out.println("<option" + Html.sel(a.getCourse(), c) + ">" + Html.esc(c) + "</option>");
        }
        out.println("</select>");
        out.println("<label>Mode of study</label><div class=\"inline\">");
        for (String m : MODES) {
            boolean checked = m.equals(a.getStudyMode()) || (a.getStudyMode() == null && m.equals("Regular"));
            out.println("<label><input type=\"radio\" name=\"studyMode\" value=\"" + m + "\""
                    + (checked ? " checked" : "") + "> " + m + "</label>");
        }
        out.println("</div>");
        out.println("<div class=\"inline\"><label><input type=\"checkbox\" name=\"hostel\" value=\"yes\""
                + (a.isHostel() ? " checked" : "") + "> Hostel accommodation required</label></div>");
        out.println("<label><input type=\"checkbox\" name=\"declare\" value=\"yes\" required>"
                + " I declare that the above information is true.</label>");
        out.println("<a class=\"btn grey\" href=\"" + resp.encodeURL("step2") + "\">&laquo; Back</a> ");
        out.println("<button type=\"submit\">Submit Application</button>");
        out.println("</form>");
        Html.footer(out);
    }

    /** Prints the personal + academic rows (and course rows if withCourse) of a summary table. */
    static void printSummaryRows(PrintWriter out, Admission a, boolean withCourse) {
        row(out, "Name", a.getName());
        row(out, "Gender", a.getGender());
        row(out, "Date of birth", a.getDob());
        row(out, "Email", a.getEmail());
        row(out, "Phone", a.getPhone());
        row(out, "Address", a.getAddress());
        out.println("<tr><th colspan=\"2\">Academic details (page 2)</th></tr>");
        row(out, "Qualification", a.getQualification());
        row(out, "University", a.getUniversity());
        row(out, "Year of passing", String.valueOf(a.getPassYear()));
        row(out, "Percentage", String.format("%.2f", a.getPercentage()));
        if (withCourse) {
            out.println("<tr><th colspan=\"2\">Course selection (page 3)</th></tr>");
            row(out, "Course", a.getCourse());
            row(out, "Mode of study", a.getStudyMode());
            row(out, "Hostel", a.isHostel() ? "Yes" : "No");
        }
    }

    private static void row(PrintWriter out, String key, String value) {
        out.println("<tr><td>" + key + "</td><td>" + Html.esc(value) + "</td></tr>");
    }
}
