# Q6 – College Web Site using Frames

**Question:** Design the following web pages of college web site and link these pages to
home page using frames in HTML - Home, About Us, Courses Offered, Admissions.

## Files
| File | Purpose |
|------|---------|
| `index.html` | **Frameset page** (open this). `rows="110,*"` gives a top header frame; the lower part is split with `cols="220,*"` into a menu frame and a content frame named `content`. Includes `<noframes>` fallback. |
| `header.html` | Top frame – college name. |
| `nav.html` | Left frame – menu links, each with `target="content"`. |
| `home.html` | Home page (loaded in the content frame by default). |
| `about.html` | About Us – vision, mission, contact. |
| `courses.html` | Courses Offered – table using `rowspan`. |
| `admissions.html` | Admissions – eligibility, dates, documents. |
| `style.css` | Shared stylesheet. |

## How to run
Open `index.html` in a web browser. Clicking Home / About Us / Courses Offered / Admissions in the
left menu loads that page in the right-hand frame while the header and menu stay fixed.

> Note: `<frameset>`/`<frame>` are obsolete in HTML5 but are still rendered by all major browsers;
> they are used here because the question asks for frames.
