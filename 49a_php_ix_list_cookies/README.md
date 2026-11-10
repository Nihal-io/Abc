# 49 (a)(ix) – List all cookies (PHP)

**Question:** A web application that lists all cookies stored in the browser on clicking
"List Cookies" button. Add cookies if necessary.

## Files
| File | Purpose |
|------|---------|
| `index.php` | The whole application: sets two sample cookies on the first visit, has a form to add a cookie with `setcookie()` (valid for 1 hour), and a **List Cookies** button that shows every cookie in `$_COOKIE` in a table |

## How to run
1. Put the folder in the web root or run `php -S localhost:8000` inside it.
2. Open `http://localhost:8000/index.php`.
3. Optionally add cookies (e.g. name `city`, value `Hyderabad`), then click **List Cookies**.

## Sample output (after adding `city = Hyderabad`)
| # | Cookie Name | Cookie Value |
|---|-------------|--------------|
| 1 | course | MCA |
| 2 | subject | Web Technologies |
| 3 | city | Hyderabad |

Total cookies: 3

## Notes
- PHP can only see the cookies the browser sends for this site (same domain and path), so the
  list shows this site's cookies – the browser never sends other sites' cookies to the server.
- A cookie set with `setcookie()` appears in `$_COOKIE` only on the **next** request, which is
  why the page redirects after adding one.
- Cookie names are restricted to letters, digits, `_` and `-`; names and values are escaped with
  `htmlspecialchars` when listed.
