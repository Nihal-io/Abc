# Q48 – Full-fledged Web Application with Database Access over 3 Sessions

**Question 48:** Develop a full-fledged web application with database access spreading over
to 3 sessions.

## Interpretation

"Spreading over 3 sessions" is implemented as a **3-page (3-step) web application** in
which the data entered on each page is **carried to the next page with `HttpSession`**,
and the complete record is written to the **MySQL database with JDBC** only at the end.

Application: **Online Student Admission**

| Step | URL | Servlet | What happens |
|------|-----|---------|--------------|
| – | `index.html` | – | Home page: *Apply Now* / *View All Admissions* |
| 1 | `/step1` | `PersonalDetailsServlet` | Personal details form → validated → stored in the session |
| 2 | `/step2` | `AcademicDetailsServlet` | Academic details form → validated → added to the same session |
| 3 | `/step3` | `CourseSelectionServlet` | Shows a **review** of pages 1 & 2 (read back from the session) + course-selection form |
| submit | `/submit` | `SubmitAdmissionServlet` | Adds page-3 data, **INSERTs** the whole application into MySQL, invalidates the session, shows the confirmation with the application number |
| list | `/admissions` | `ListAdmissionsServlet` | **SELECT**s and lists all admissions from the database |

How the session is used:

- A single JavaBean `Admission` is stored in the session as attribute `admission`
  and filled page by page.
- `completedStep` in the bean stops the user from jumping to page 2/3 (or submitting)
  without finishing earlier pages — they are redirected back.
- *Back* / *Edit* links re-open earlier pages **pre-filled** from the session.
- Each POST redirects to the next page (Post/Redirect/Get), so refreshing does not resubmit.
- `response.encodeURL()` / `encodeRedirectURL()` keep the session working even if cookies are off.
- After a successful insert `session.invalidate()` ends the session.

## Files

| File | Purpose |
|------|---------|
| `index.html` | Home page |
| `style.css` | Shared stylesheet |
| `src/Admission.java` | Serializable bean kept in the session |
| `src/DBUtil.java` | JDBC connection helper (`labdb`, `root` / `root`) |
| `src/Html.java` | Shared page header/footer, step bar, HTML escaping |
| `src/PersonalDetailsServlet.java` | Page 1 (`/step1`) |
| `src/AcademicDetailsServlet.java` | Page 2 (`/step2`) |
| `src/CourseSelectionServlet.java` | Page 3 + review (`/step3`) |
| `src/SubmitAdmissionServlet.java` | Final submit + DB insert (`/submit`) |
| `src/ListAdmissionsServlet.java` | List of admissions from DB (`/admissions`) |
| `db.sql` | Creates database `labdb`, table `admissions`, 2 sample rows |

## How to run (Apache Tomcat 9 + MySQL)

```bash
# 1. Database (change user/password in src/DBUtil.java if yours are not root/root)
mysql -u root -p < db.sql

# 2. Create the web app in Tomcat
APP=$CATALINA_HOME/webapps/48_three_session_web_app
mkdir -p $APP/WEB-INF/classes $APP/WEB-INF/lib
cp index.html style.css $APP/
cp mysql-connector-j-8.x.jar $APP/WEB-INF/lib/          # JDBC driver

# 3. Compile all servlets into WEB-INF/classes
javac -cp $CATALINA_HOME/lib/servlet-api.jar -d $APP/WEB-INF/classes src/*.java

# 4. Start Tomcat
$CATALINA_HOME/bin/startup.sh
```

Open: <http://localhost:8080/48_three_session_web_app/>

## Sample run

**Page 1 – Personal details:** Name `Arjun Das`, Male, 15-06-2003, `arjun@example.com`,
`9988776655`, `Kottayam, Kerala` → *Next*

**Page 2 – Academic details:** BCA, MG University, 2025, 79.5 → *Next*

**Page 3 – Review + course:** the table shows all the page 1 and page 2 values taken from
the session; choose `MCA`, Regular, hostel ✔, tick the declaration → *Submit Application*

**Confirmation:**

```
Thank you, Arjun Das! Your application has been saved.
Application number: ADM0003
(full summary of all 3 pages)
```

**View All Admissions** (`/admissions`):

```
App No  Name        Gender  DOB         Email              Phone       Qualification          %      Course             Mode     Hostel  Applied on
ADM0001 Anu Mathew  Female  12-04-2003  anu@example.com    9876543210  BCA                    82.50  MCA                Regular  Yes     10-11-2026 10:00
ADM0002 Rahul Nair  Male    30-09-2002  rahul@example.com  9123456780  B.Sc Computer Science  76.40  M.Sc Data Science  Regular  No      10-11-2026 10:00
ADM0003 Arjun Das   Male    15-06-2003  arjun@example.com  9988776655  BCA                    79.50  MCA                Regular  Yes     10-11-2026 10:42
Total: 3 application(s)
```

Trying to open `/step3` directly in a new browser redirects to `/step1`, because no session
data exists yet.
