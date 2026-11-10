# Q5 – Online Shopping Home Page

**Question:** Design a Home page of online Shopping web site that contains different products
with images. Create image of the products as hyperlinks to display the Description of products.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Home page: header, banner and a grid of 4 products. Each product **image is wrapped in `<a href>`** linking to its description page. |
| `laptop.html`, `mobile.html`, `headphones.html`, `watch.html` | One description page per product (image, price, description, specification table, Add to Cart button, link back home). |
| `style.css` | External stylesheet shared by all pages. |
| `images/*.svg` | Local SVG product images and the site logo (no internet needed). |

## How to run
Open `index.html` in any web browser (double-click it). No server is required.

## Expected output
- Home page shows 4 product cards (Laptop, Smartphone, Headphones, Smart Watch) with prices.
- Clicking a product **image** (or *View Details*) opens that product's description page.
- On a description page, *Add to Cart* shows an alert, and *Back / Continue Shopping* returns home.
