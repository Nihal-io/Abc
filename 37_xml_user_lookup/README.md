# Q37 – User lookup from an XML document on the server

**Question 37:** Create and save an XML document at the server, which contains 10 users
information. Write a program which takes User Id as input and returns the user details by taking
the user information from the XML document.

## Files
| File | Purpose |
|------|---------|
| `users.xml` | XML document stored on the server with 10 `<user>` records (IDs 101–110). Each record has `userid`, `name`, `email`, `phone`, `city`, `course` and `dob`. |
| `index.html` | Client-side version. It requests `users.xml` from the server with `XMLHttpRequest`, parses it with `DOMParser`, loops over the `<user>` nodes and shows the one whose `<userid>` matches the input. A second button sends the same lookup to `getuser.php`. |
| `getuser.php` | Server-side version. It loads `users.xml` with **SimpleXML**, finds the user with the XPath query `/users/user[userid='ID']` and returns the details. `getuser.php?id=103` returns an HTML page with a form; `getuser.php?id=103&format=json` returns JSON. The ID is checked with `ctype_digit` before it goes into the query. |

## How to run
The page loads `users.xml` with XHR, so it must be served over HTTP. Opening it as `file://` will not work.

**Option A – JavaScript only:**
```bash
cd 37_xml_user_lookup
python3 -m http.server 8000
# open http://localhost:8000/  and use "Search (JavaScript + XML)"
```

**Option B – PHP (does both):**
```bash
cd 37_xml_user_lookup
php -S localhost:8000
# open http://localhost:8000/getuser.php?id=105      (pure PHP page)
# or    http://localhost:8000/   and use either button
```
You can also copy the folder into `htdocs` (XAMPP) or `/var/www/html` (Apache) and open it there.

## Sample input / output (tested)
`getuser.php?id=103&format=json`
```json
{"found":true,"user":{"User ID":"103","Name":"Arjun Reddy","E-mail":"arjun.reddy@example.com","Phone":"9988776655","City":"Bengaluru","Course":"MCA","Date of Birth":"1998-11-02"}}
```
`getuser.php?id=999&format=json` → `{"found":false,"message":"No user found with ID 999."}`

`getuser.php?id=abc&format=json` → `{"found":false,"message":"User ID must be a number."}`

Entering **105** in `index.html` shows:

| Field | Value |
|---|---|
| User ID | 105 |
| Name | Mohammed Irfan |
| E-mail | irfan.m@example.com |
| Phone | 9345678123 |
| City | Chennai |
| Course | MCA |
| Date of Birth | 2000-03-15 |
