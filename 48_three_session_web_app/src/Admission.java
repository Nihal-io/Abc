/*
 * Question 48: Develop a full-fledged web application with database access spreading over to 3 sessions.
 *
 * Admission is a simple JavaBean that holds the applicant's data. ONE object is kept in the
 * HttpSession (attribute "admission") and is filled step by step:
 *   step 1 -> personal details, step 2 -> academic details, step 3 -> course selection.
 * It implements Serializable so the container can persist/replicate the session.
 */

import java.io.Serializable;

public class Admission implements Serializable {

    private static final long serialVersionUID = 1L;

    // Step 1 - personal details
    private String name;
    private String gender;
    private String dob;          // yyyy-MM-dd (from <input type="date">)
    private String email;
    private String phone;
    private String address;

    // Step 2 - academic details
    private String qualification;
    private String university;
    private int passYear;
    private double percentage;

    // Step 3 - course selection
    private String course;
    private String studyMode;
    private boolean hostel;

    /** Which steps are complete (used to stop users jumping ahead). */
    private int completedStep;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }
    public int getPassYear() { return passYear; }
    public void setPassYear(int passYear) { this.passYear = passYear; }
    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getStudyMode() { return studyMode; }
    public void setStudyMode(String studyMode) { this.studyMode = studyMode; }
    public boolean isHostel() { return hostel; }
    public void setHostel(boolean hostel) { this.hostel = hostel; }

    public int getCompletedStep() { return completedStep; }
    public void setCompletedStep(int completedStep) { this.completedStep = completedStep; }
}
