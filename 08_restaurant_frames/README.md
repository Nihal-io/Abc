# Q8 – Restaurant Web Page using Frames

**Question:** Use frames to implement a restaurant webpage.

## Files
| File | Purpose |
|------|---------|
| `index.html` | **Frameset page** (open this). Top frame = header; bottom split into a left navigation frame and a right content frame named `main`. Has a `<noframes>` fallback. |
| `header.html` | Top frame – restaurant name and tagline. |
| `nav.html` | Left frame – links with `target="main"`. |
| `home.html` | Welcome page (default content). |
| `menu.html` | Full menu with prices (veg / non-veg). |
| `specials.html` | Today's chef specials. |
| `contact.html` | Address and a table-booking form (shows an alert on submit). |
| `style.css` | Shared stylesheet. |

## How to run
Open `index.html` in a web browser. Clicking Home / Our Menu / Today's Specials / Contact in the
left frame loads the page into the right frame; header and menu remain fixed.

> `<frameset>` is obsolete in HTML5 but still supported by browsers; it is used because the
> question asks for frames.
