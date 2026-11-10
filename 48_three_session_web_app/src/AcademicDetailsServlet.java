/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Page 2 of 3 - Academic details  (URL: /step2)
 *   GET  : shows the academic-details form. Only allowed after page 1 is complete
 *          (otherwise the user is sent back to page 1).
 *   POST : validates the data, adds it to the Admission bean in the same HttpSession
 *          and redirects to page 3 (/step3).
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/step2")
public class AcademicDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String[] QUALIFICATIONS = {"BCA", "B.Sc Computer Science", "B.Sc Mathematics",
        "B.Com", "B.Tech", "BBA", "Other"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Admission a = requireStep1(req, resp);
        if (a != null) {
            showForm(resp, a, null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Admission a = requireStep1(req, resp);
        if (a == null) {
            return;
        }

        a.setQualification(Html.param(req, "qualification"));
        a.setUniversity(Html.param(req, "university"));
        String error = null;
        try {
            a.setPassYear(Integer.parseInt(Html.param(req, "passYear")));
            a.setPercentage(Double.parseDouble(Html.param(req, "percentage")));
        } catch (NumberFormatException e) {
            error = "Year of passing and percentage must be numbers.";
        }
        if (error == null) {
            error = validate(a);
        }
        if (error != null) {
            a.setCompletedStep(1);                               // page 2 must be fixed first
            showForm(resp, a, error);                            // stay on page 2
            return;
        }
        a.setCompletedStep(Math.max(a.getCompletedStep(), 2));
        resp.sendRedirect(resp.encodeRedirectURL("step3"));      // go to page 3
    }

    /** Returns the session's Admission bean if page 1 is done, otherwise redirects to page 1. */
    private static Admission requireStep1(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        Admission a = session == null ? null : (Admission) session.getAttribute("admission");
        if (a == null || a.getCompletedStep() < 1) {
            resp.sendRedirect(resp.encodeRedirectURL("step1"));
            return null;
        }
        return a;
    }

    private static String validate(Admission a) {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        if (a.getQualification() == null || a.getQualification().isEmpty()
                || a.getUniversity() == null || a.getUniversity().isEmpty()) {
            return "Qualification and university are required.";
        }
        if (a.getPassYear() < 1980 || a.getPassYear() > thisYear) {
            return "Year of passing must be between 1980 and " + thisYear + ".";
        }
        if (a.getPercentage() < 0 || a.getPercentage() > 100) {
            return "Percentage must be between 0 and 100.";
        }
        return null;
    }

    private void showForm(HttpServletResponse resp, Admission a, String error) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.header(out, "Step 2 - Academic Details", false);
        Html.steps(out, 2);
        out.println("<h2>Academic Details</h2>");
        out.println("<p class=\"small\">Applicant: <b>" + Html.esc(a.getName()) + "</b></p>");
        if (error != null) {
            out.println("<p class=\"err\">" + Html.esc(error) + "</p>");
        }
        out.println("<form action=\"" + resp.encodeURL("step2") + "\" method=\"post\">");
        out.println("<label for=\"qualification\">Qualifying degree</label>");
        out.println("<select id=\"qualification\" name=\"qualification\" required>");
        out.println("<option value=\"\">-- select --</option>");
        for (String q : QUALIFICATIONS) {
            out.println("<option" + Html.sel(a.getQualification(), q) + ">" + Html.esc(q) + "</option>");
        }
        out.println("</select>");
        out.println("<label for=\"university\">University / Board</label>");
        out.println("<input type=\"text\" id=\"university\" name=\"university\" required maxlength=\"80\""
                + " value=\"" + Html.esc(a.getUniversity()) + "\">");
        out.println("<label for=\"passYear\">Year of passing</label>");
        out.println("<input type=\"number\" id=\"passYear\" name=\"passYear\" required min=\"1980\""
                + " max=\"" + Calendar.getInstance().get(Calendar.YEAR) + "\" value=\""
                + (a.getPassYear() == 0 ? "" : String.valueOf(a.getPassYear())) + "\">");
        out.println("<label for=\"percentage\">Percentage of marks</label>");
        out.println("<input type=\"number\" id=\"percentage\" name=\"percentage\" required min=\"0\""
                + " max=\"100\" step=\"0.01\" value=\""
                + (a.getPassYear() == 0 ? "" : String.valueOf(a.getPercentage()))
                + "\">");
        out.println("<a class=\"btn grey\" href=\"" + resp.encodeURL("step1") + "\">&laquo; Back</a> ");
        out.println("<button type=\"submit\">Next &raquo;</button>");
        out.println("</form>");
        Html.footer(out);
    }
}
