# Q10 – Inline, Internal and External Style Sheets

**Question:** Develop and demonstrate the usage of inline, internal and external style sheet using CSS.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Page with three boxes: one styled by the **external** sheet, one by the **internal** `<style>` block, and one by **inline** `style="..."` attributes. Also shows which one wins when they conflict. |
| `external.css` | The external style sheet, linked with `<link rel="stylesheet" href="external.css">`. |

## How to run
Keep both files in the same folder and open `index.html` in a web browser.

## Expected output
- Green/teal box → external CSS.
- Orange dashed box → internal CSS.
- Red box → inline CSS; a paragraph with both a class and an inline style shows the inline style winning.
- The `<h1>` is blue (internal sheet overrides the green from the external sheet because it comes later).
