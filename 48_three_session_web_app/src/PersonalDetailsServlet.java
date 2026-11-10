/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Page 1 of 3 - Personal details  (URL: /step1)
 *   GET  : shows the personal-details form (pre-filled from the session if the user came back)
 *   POST : validates the data, stores it in the Admission bean kept in the HttpSession
 *          and redirects to page 2 (/step2).
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/step1")
public class PersonalDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);           // the session starts here
        Admission a = getAdmission(session);
        showForm(resp, a, null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);
        Admission a = getAdmission(session);

        a.setName(Html.param(req, "name"));
        a.setGender(Html.param(req, "gender"));
        a.setDob(Html.param(req, "dob"));
        a.setEmail(Html.param(req, "email"));
        a.setPhone(Html.param(req, "phone"));
        a.setAddress(Html.param(req, "address"));

        String error = validate(a);
        if (error != null) {
            a.setCompletedStep(0);                              // page 1 must be fixed first
            showForm(resp, a, error);                           // stay on page 1
            return;
        }
        a.setCompletedStep(Math.max(a.getCompletedStep(), 1));
        resp.sendRedirect(resp.encodeRedirectURL("step2"));     // go to page 2
    }

    /** Returns the Admission bean from the session, creating it on the first visit. */
    static Admission getAdmission(HttpSession session) {
        Admission a = (Admission) session.getAttribute("admission");
        if (a == null) {
            a = new Admission();
            session.setAttribute("admission", a);
        }
        return a;
    }

    private static String validate(Admission a) {
        if (a.getName().isEmpty() || a.getGender().isEmpty() || a.getDob().isEmpty()
                || a.getEmail().isEmpty() || a.getPhone().isEmpty() || a.getAddress().isEmpty()) {
            return "All fields are required.";
        }
        if (!a.getEmail().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            return "Please enter a valid email address.";
        }
        if (!a.getPhone().matches("\\d{10}")) {
            return "Phone number must contain exactly 10 digits.";
        }
        try {
            java.sql.Date.valueOf(a.getDob());                  // expects yyyy-MM-dd
        } catch (IllegalArgumentException e) {
            return "Please enter a valid date of birth.";
        }
        return null;
    }

    private void showForm(HttpServletResponse resp, Admission a, String error) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Html.header(out, "Step 1 - Personal Details", false);
        Html.steps(out, 1);
        out.println("<h2>Personal Details</h2>");
        if (error != null) {
            out.println("<p class=\"err\">" + Html.esc(error) + "</p>");
        }
        String g = a.getGender();
        out.println("<form action=\"" + resp.encodeURL("step1") + "\" method=\"post\">");
        out.println("<label for=\"name\">Full name</label>");
        out.println("<input type=\"text\" id=\"name\" name=\"name\" required maxlength=\"50\" value=\""
                + Html.esc(a.getName()) + "\">");
        out.println("<label>Gender</label><div class=\"inline\">");
        for (String opt : new String[] {"Male", "Female", "Other"}) {
            out.println("<label><input type=\"radio\" name=\"gender\" value=\"" + opt + "\" required"
                    + (opt.equals(g) ? " checked" : "") + "> " + opt + "</label>");
        }
        out.println("</div>");
        out.println("<label for=\"dob\">Date of birth</label>");
        out.println("<input type=\"date\" id=\"dob\" name=\"dob\" required value=\""
                + Html.esc(a.getDob()) + "\">");
        out.println("<label for=\"email\">Email</label>");
        out.println("<input type=\"email\" id=\"email\" name=\"email\" required maxlength=\"60\" value=\""
                + Html.esc(a.getEmail()) + "\">");
        out.println("<label for=\"phone\">Phone (10 digits)</label>");
        out.println("<input type=\"text\" id=\"phone\" name=\"phone\" required pattern=\"[0-9]{10}\""
                + " maxlength=\"10\" value=\"" + Html.esc(a.getPhone()) + "\">");
        out.println("<label for=\"address\">Address</label>");
        out.println("<textarea id=\"address\" name=\"address\" rows=\"3\" required maxlength=\"200\">"
                + Html.esc(a.getAddress()) + "</textarea>");
        out.println("<a class=\"btn grey\" href=\"index.html\">Cancel</a> ");
        out.println("<button type=\"submit\">Next &raquo;</button>");
        out.println("</form>");
        Html.footer(out);
    }
}
