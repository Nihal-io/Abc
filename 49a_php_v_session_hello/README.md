# 49 (a)(v) – Hello page with session start time and logout duration (PHP)

**Question:** A web application takes a name as input and on submit it shows a hello&lt;name&gt;
page where &lt;name&gt; is taken from the request. It shows the start time at the right top corner of
the page and provides the logout button. On clicking this button, it should show a logout page
with Thank You&lt;name&gt; message with the duration of usage. (Use session to store name and time).

## Files
| File | Purpose |
|------|---------|
| `index.html` | Form asking for the name, POSTs to `hello.php` |
| `hello.php` | `session_start()`; saves `$_SESSION['name']` and `$_SESSION['start'] = time()`; shows "Hello &lt;name&gt;", the start time in the top-right corner (CSS `position:absolute; top; right`) and a Logout button |
| `logout.php` | Reads name and start time from the session, computes `time() - start`, destroys the session and shows "Thank You &lt;name&gt;" with the duration |

## How to run
1. Put the folder in the web root or run `php -S localhost:8000` inside it.
2. Open `http://localhost:8000/index.html`, enter a name, wait a little, then click **Logout**.

## Sample input / output
Input: `Ravi`

Hello page:
```
                                         Start time: 10-11-2026 10:15:02 AM
                Hello Ravi
   You can browse the site. Click Logout when you are done.
                [ Logout ]
```

Logout page:
```
Thank You Ravi
Start time: 10-11-2026 10:15:02 AM
End time: 10-11-2026 10:17:45 AM
Duration of usage: 0 hour(s) 2 minute(s) 43 second(s)
```

## Notes
- The time zone is set to `Asia/Kolkata` with `date_default_timezone_set()`; change it if needed.
- Refreshing `hello.php` keeps the original start time, because it comes from the session.
- Opening `hello.php` or `logout.php` without a session redirects to `index.html`.
