# Q45 – Java Servlet Application with Session Tracking

**Question 45:** Develop a Java Servlet application with session tracking.

HTTP is stateless, so the server needs a way to recognise requests coming from the same
user. This application uses the Servlet API's **`HttpSession`** to remember data between
requests:

- the user's name (entered on `index.html`)
- a **visit counter** that increases on every page refresh
- **session id**, **first-visit (creation) time**, **last access time**,
  max inactive interval and whether the session is new
- a **logout** link that calls `session.invalidate()`

## Files

| File | Purpose |
|------|---------|
| `index.html` | Form to enter a name and start the session |
| `src/SessionServlet.java` | `/session` – creates/reads the `HttpSession`, counts visits, shows session details |
| `src/LogoutServlet.java` | `/logout` – invalidates the session |

## Key `HttpSession` methods used

| Method | Use |
|--------|-----|
| `request.getSession(true)` | get the current session or create a new one |
| `request.getSession(false)` | get the current session, `null` if none (used in logout) |
| `setAttribute()` / `getAttribute()` | store / read `user` and `visits` |
| `getId()` | unique session id (sent to the browser as the `JSESSIONID` cookie) |
| `getCreationTime()` / `getLastAccessedTime()` | first visit / previous access time |
| `isNew()` | `true` on the first request of the session |
| `getMaxInactiveInterval()` | timeout in seconds (Tomcat default 1800 = 30 min) |
| `invalidate()` | destroys the session (logout) |
| `response.encodeURL()` | adds `;jsessionid=...` to links if cookies are disabled |

## Other session-tracking techniques (for the record)

1. **Cookies** – `new Cookie("user", name)` added with `response.addCookie()` and read back
   with `request.getCookies()`. `HttpSession` itself uses the `JSESSIONID` cookie.
2. **URL rewriting** – the session id / data is appended to every URL
   (`page?user=Ravi` or `;jsessionid=...` via `response.encodeURL()`, used in this app).
3. **Hidden form fields** – `<input type="hidden" name="user" value="Ravi">` carried in
   every form submitted to the server.
4. **HttpSession** – server-side object identified by an id (used in this app).

## How to run (Apache Tomcat 9)

```bash
mkdir -p $CATALINA_HOME/webapps/45_servlet_session_tracking/WEB-INF/classes
cp index.html $CATALINA_HOME/webapps/45_servlet_session_tracking/
javac -cp $CATALINA_HOME/lib/servlet-api.jar \
      -d $CATALINA_HOME/webapps/45_servlet_session_tracking/WEB-INF/classes src/*.java
$CATALINA_HOME/bin/startup.sh
```

Open: <http://localhost:8080/45_servlet_session_tracking/>

## Sample output

After entering `Ravi` and refreshing twice:

```
Welcome back, Ravi!
Property                      Value
Session ID                    8F3C1A0B5D2E47F09A6C1E2B3D4F5A6B
New session?                  false
Visit count (this session)    3
First visit (creation time)   10-11-2026 10:20:01
Previous access time          10-11-2026 10:20:15
Current server time           10-11-2026 10:20:22
Max inactive interval         1800 seconds
Session id from cookie?       true
Session id from URL?          false

Refresh (visit again) | Logout
```

After **Logout**:

```
You have been logged out
Session 8F3C1A0B5D2E47F09A6C1E2B3D4F5A6B invalidated after 3 visit(s).
```

Visiting `/session` again now shows a new session id and `Visit count = 1`.
Opening the page in a different browser (or a private window) also gives a separate session.
