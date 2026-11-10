# Q13 - Dynamic background colour (inline style) + toggling external stylesheets

**Question:** Write a program that changes the background color of a webpage dynamically
using inline styles, and also includes a button to toggle between two external stylesheets.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Page with colour buttons, a colour picker and the stylesheet toggle button (JavaScript embedded) |
| `style1.css` | External stylesheet 1 - light "Classic" theme |
| `style2.css` | External stylesheet 2 - dark "Night" theme |

## How it works
- **Inline style:** `document.body.style.backgroundColor = color` adds/changes the
  `style="background-color: ..."` attribute on `<body>`. The page shows the current inline style.
  "Reset" removes the inline style so the stylesheet's own background shows again.
- **Stylesheet toggle:** the `<link id="themeSheet">` element's `href` is switched between
  `style1.css` and `style2.css`.

## How to run
Keep the three files in the same folder and open `index.html` in any browser.

## Sample output
- Click **Light Green** -> page background turns light green; "Current inline style" shows
  `background-color: lightgreen;`.
- Click **Switch to Style 2** -> fonts, headings, buttons and the box change to the dark theme;
  "Active stylesheet" shows `style2.css`.
