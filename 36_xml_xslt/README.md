# Q36 – XML and XSLT

**Question 36:** Write a program to illustrate XML and XSLT.

## Files
| File | Purpose |
|------|---------|
| `books.xml` | XML data: a `<library>` of 7 `<book>` elements, each with `id` and `category` attributes and child elements. Its second line is `<?xml-stylesheet type="text/xsl" href="books.xsl"?>`, which links it to the stylesheet. |
| `books.xsl` | XSLT 1.0 stylesheet that turns the XML into an HTML table. It uses `xsl:template`, `xsl:for-each`, `xsl:sort` (by category, then by price descending), `xsl:value-of`, `xsl:if` (marks books over Rs. 1000 and adds a "New" tag for 2021+), `xsl:choose/when/otherwise` (stock status), `xsl:attribute`, `count()`, `sum()` and `format-number()`. |
| `index.html` | Loads both files with `fetch`, parses them with `DOMParser`, and applies the stylesheet in JavaScript using `XSLTProcessor` (`importStylesheet` + `transformToFragment`). It can also show the raw XML/XSL. |

## How to run
For security reasons, browsers (Chrome especially) block XSLT and XML loading for pages opened as
`file://`. Serve the folder from a local web server instead:

```bash
cd 36_xml_xslt
python3 -m http.server 8000
```
Then open:
- `http://localhost:8000/books.xml`: the browser sees the `<?xml-stylesheet?>` instruction and shows the transformed table.
- `http://localhost:8000/` (`index.html`): the same transformation, done with JavaScript `XSLTProcessor`.

(Optional command-line check: `xsltproc books.xsl books.xml`, or Python `lxml.etree.XSLT`.)

## Sample output (tested with lxml)
```
Library Book Catalog
Total books: 7 | Total copies in stock: 54

ID    Title                                   Category     Price      Stock
B104  Database System Concepts                Database     780.00     Only 3 left
B107  Computer Networks [New]                 Networking   950.00     7 available
B101  Java: The Complete Reference [New]      Programming  899.00     12 available
B105  Let Us C [New]                          Programming  399.00     25 available
B103  JavaScript: The Definitive Guide        Web          1,250.00   5 available   (red row)
B106  Learning PHP, MySQL and JavaScript [New] Web         1,100.00   Only 2 left   (red row)
B102  HTML and CSS: Design and Build Websites Web          650.00     Out of stock
```
