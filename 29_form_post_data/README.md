# Q29 – Handling form data submitted via POST

**Question 29:** Write a Java script that can handle form data submitted via post.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Student form with `method="post"` and `action="/submit"`. JavaScript catches the `submit` event, reads all fields with `FormData` and shows them in a table, along with the urlencoded body a POST would send. |
| `server.js` | Small Node.js server that uses only the built-in `http` module (no Express). `GET /` sends the form; `POST /submit` reads the request body, parses it with `URLSearchParams` and replies with the data. |

## Two ways to run it

### 1. Client side only (no server)
Open `index.html` in a browser and click **Submit (handled by JavaScript)**.
`event.preventDefault()` stops the page from reloading. The script then builds
`new FormData(form)` and displays every field. Checkbox fields with more than one value are joined.

### 2. With the Node.js server (real POST)
```bash
cd 29_form_post_data
node server.js            # Server running at http://localhost:3000/
```
Open **http://localhost:3000/**, which loads the form from the server, so the POST is not cross-origin.
- **Submit (handled by JavaScript)** shows the FormData table and also sends the data with
  `fetch()` as `application/x-www-form-urlencoded`. The server replies with JSON, which the page shows.
- **Submit directly to server (normal POST)** does an ordinary browser form POST. The server
  replies with an HTML page that lists the data it received.

The server also prints each received POST in the terminal.

## Sample input / output
Input: Name = `Ravi Kumar`, E-mail = `ravi@gmail.com`, Age = `22`, Gender = Male, Course = MCA,
Hobbies = Reading + Coding, Comments = `Good lab`

Request body sent:
```
name=Ravi+Kumar&email=ravi%40gmail.com&age=22&gender=Male&course=MCA&hobbies=Reading&hobbies=Coding&comments=Good+lab
```
Server JSON response (tested with curl):
```json
{"status":"received","method":"POST","data":{"name":"Ravi Kumar","email":"ravi@gmail.com","age":"22","gender":"Male","course":"MCA","hobbies":["Reading","Coding"],"comments":"Good lab"}}
```
Server console:
```
POST /submit received: { name: 'Ravi Kumar', email: 'ravi@gmail.com', age: '22', gender: 'Male',
  course: 'MCA', hobbies: [ 'Reading', 'Coding' ], comments: 'Good lab' }
```
