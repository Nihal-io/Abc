/*
 * Question 29: Write a Java script that can handle form data submitted via post.
 *
 * Server side part: a tiny Node.js server using ONLY the built-in "http" module.
 *   GET  /        -> sends index.html (the form)
 *   POST /submit  -> reads the request body, parses the urlencoded form data
 *                    and responds with the received data (HTML page, or JSON
 *                    when the client sends "Accept: application/json").
 *
 * Run:  node server.js     then open  http://localhost:3000/
 */
const http = require("http");
const fs = require("fs");
const path = require("path");

const PORT = process.env.PORT || 3000;

// Escape text before putting it inside HTML
function esc(s) {
  return String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}

// Parse "a=1&b=2&b=3" into { a: "1", b: ["2", "3"] }
function parseUrlEncoded(body) {
  const params = new URLSearchParams(body);
  const result = {};
  for (const [key, value] of params) {
    if (key in result) {
      result[key] = [].concat(result[key], value);
    } else {
      result[key] = value;
    }
  }
  return result;
}

const server = http.createServer((req, res) => {
  // ---- GET / : serve the form page ----
  if (req.method === "GET" && (req.url === "/" || req.url === "/index.html")) {
    fs.readFile(path.join(__dirname, "index.html"), (err, html) => {
      if (err) {
        res.writeHead(500, { "Content-Type": "text/plain" });
        return res.end("Cannot read index.html");
      }
      res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
      res.end(html);
    });
    return;
  }

  // ---- POST /submit : receive form data ----
  if (req.method === "POST" && req.url === "/submit") {
    let body = "";
    req.on("data", (chunk) => {
      body += chunk;
      if (body.length > 1e6) req.destroy(); // protect against huge bodies
    });
    req.on("end", () => {
      const data = parseUrlEncoded(body);
      console.log("POST /submit received:", data);

      const wantsJson = (req.headers["accept"] || "").includes("application/json");
      if (wantsJson) {
        res.writeHead(200, { "Content-Type": "application/json" });
        res.end(JSON.stringify({ status: "received", method: req.method, data: data }));
        return;
      }

      // Normal browser form post -> reply with an HTML page
      let rows = "";
      for (const key in data) {
        const value = Array.isArray(data[key]) ? data[key].join(", ") : data[key];
        rows += `<tr><td>${esc(key)}</td><td>${esc(value)}</td></tr>`;
      }
      res.writeHead(200, { "Content-Type": "text/html; charset=utf-8" });
      res.end(`<!DOCTYPE html>
<html lang="en"><head><meta charset="UTF-8"><title>Form data received</title>
<style>body{font-family:Arial;margin:20px}table{border-collapse:collapse}
td,th{border:1px solid #999;padding:6px 10px}th{background:#2c3e50;color:#fff}</style></head>
<body><h2>Server received the following POST data</h2>
<table><tr><th>Field</th><th>Value</th></tr>${rows || "<tr><td colspan='2'>(no data)</td></tr>"}</table>
<p><b>Raw body:</b> ${esc(body)}</p>
<p><a href="/">Back to form</a></p></body></html>`);
    });
    return;
  }

  // ---- Anything else ----
  res.writeHead(404, { "Content-Type": "text/plain" });
  res.end("404 Not Found");
});

server.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}/`);
});
