# Q39 – "Raw" AJAX with XMLHttpRequest and an XML file

**Question 39:** Write a program on "raw" AJAX implementation using the XMLHttpRequest object to
fetch and display data from an XML file.

## Files
| File | Purpose |
|------|---------|
| `employees.xml` | XML data: `<company name="...">` with 6 `<employee id="...">` records (name, designation, department, salary, city). |
| `index.html` | Hand-written AJAX with no libraries and no `fetch`. It creates an `XMLHttpRequest` (with the old IE `ActiveXObject` fallback), assigns `onreadystatechange`, calls `open("GET", url, true)` and `send()`. When `readyState === 4` and `status === 200`, it walks `responseXML` with `getElementsByTagName` / `getAttribute` and builds an HTML table with a salary total. Every readyState change is logged on the page. A second button requests a missing file to show the error path (status 404). |

## How to run
Browsers block XHR for pages opened as `file://`, so serve the folder:
```bash
cd 39_raw_ajax_xml
python3 -m http.server 8000
# open http://localhost:8000/  and click "Load Employees"
```

## Sample output
```
TechSoft Solutions - 6 employees
ID   Name            Designation        Department   City       Salary (Rs.)
E01  Ravi Kumar      Software Engineer  Development  Hyderabad  55,000
E02  Priya Sharma    Team Lead          Development  Bengaluru  85,000
E03  Arjun Reddy     Test Engineer      Testing      Chennai    45,000
E04  Sneha Patel     UI Designer        Design       Pune       50,000
E05  Mohammed Irfan  DevOps Engineer    Operations   Hyderabad  65,000
E06  Kavya Nair      HR Executive       HR           Kochi      40,000
Total salary                                                   3,40,000

readyState log:
readyState = 1 OPENED
request sent for employees.xml
readyState = 2 HEADERS_RECEIVED
readyState = 3 LOADING
readyState = 4 DONE
status = 200 OK
```
(`readyState = 1 OPENED` comes before "request sent" because `open()` fires the event before `send()` is called.)

Clicking **Load a missing file** shows `status = 404 File not found` and the message "Error 404: could not load missing.xml".
