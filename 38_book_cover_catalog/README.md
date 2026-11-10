# Q38 – Book-cover catalog with titles on hover

**Question 38:** Create an XML files with book-cover catalog and store the titles of the books in a
title attribute of each cover node. Modify the example so that every time the mouse hovers over an
image, the book's title is displayed below the image.

## Files
| File | Purpose |
|------|---------|
| `catalog.xml` | Catalog of 6 `<cover>` nodes, e.g. `<cover title="XML Bible" author="..." src="images/xml_bible.svg"/>`. The book title is kept in the `title` **attribute**. |
| `images/*.svg` | Six small local SVG book covers, so no external image URLs are needed. |
| `index.html` | Loads `catalog.xml` with `XMLHttpRequest`. For each `<cover>` it reads `getAttribute("title")` and `getAttribute("src")` and creates an `<img>` with an empty caption `<div>` under it. `onmouseover` writes the title into the caption and `onmouseout` clears it. |

## How to run
The page loads the XML with XHR, so serve the folder over HTTP:
```bash
cd 38_book_cover_catalog
python3 -m http.server 8000
# open http://localhost:8000/
```

## Sample output
Six covers appear in a row. When the mouse is over the blue "XML" cover, the text
**XML Bible** appears under it. When the mouse leaves, the text disappears. Every cover works the same way
(for example, "Java: The Complete Reference" or "Computer Networks").
