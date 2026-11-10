# Q44 – Simple Java Servlet Application

**Question 44:** Develop a simple Java Servlet application.

A form (`index.html`) takes the user's name and sends it to `HelloServlet`, which replies
with a greeting and the current server time. The servlet implements both `doGet()` and
`doPost()` and prints its life-cycle (`init()` → `doGet()/doPost()` → `destroy()`) to the
Tomcat console.

## Files

| File | Purpose |
|------|---------|
| `index.html` | Front page with two forms (one GET, one POST) taking a name |
| `src/HelloServlet.java` | Servlet mapped to `/hello` with `@WebServlet` |

## Servlet life cycle (shown in the console)

1. **Loading & instantiation** – the container creates one object of `HelloServlet`.
2. **`init()`** – called once; prints `[HelloServlet] init() called ...`.
3. **`service()`** – called for every request; dispatches to `doGet()` or `doPost()`.
4. **`destroy()`** – called once when Tomcat stops / the app is undeployed.

## How to run (Apache Tomcat 9)

```bash
# 1. Create the web application inside Tomcat
mkdir -p $CATALINA_HOME/webapps/44_simple_servlet/WEB-INF/classes
cp index.html $CATALINA_HOME/webapps/44_simple_servlet/

# 2. Compile the servlet straight into WEB-INF/classes
javac -cp $CATALINA_HOME/lib/servlet-api.jar \
      -d $CATALINA_HOME/webapps/44_simple_servlet/WEB-INF/classes src/HelloServlet.java

# 3. Start Tomcat
$CATALINA_HOME/bin/startup.sh        # Windows: startup.bat
```

Open: <http://localhost:8080/44_simple_servlet/>

(Annotations are used, so no `web.xml` is required. In Eclipse/NetBeans: create a
*Dynamic Web Project*, put the class in `src`, `index.html` in `WebContent`/`webapp`, and
run on Tomcat.)

## Sample output

Input: name = `Ravi` (POST form)

```
Hello, Ravi!
Welcome to Java Servlets.
Server time: 10-11-2026 10:15:42
Request method: POST
Requests served by this servlet instance: 1
```

Tomcat console:

```
[HelloServlet] init() called - servlet loaded at 10-11-2026 10:15:42
[HelloServlet] doPost() called
[HelloServlet] doGet() called
[HelloServlet] destroy() called - served 2 request(s). Unloaded at 10-11-2026 10:30:05
```
