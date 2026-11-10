# Q14 - Motion Blur filter on pictures and text

**Question:** Write a program to create blurred pictures and text with different direction and
strength using Motion Blur filter in CSS.

## Files
| File | Purpose |
|------|---------|
| `index.html` | The demo page (CSS, SVG filter definitions and a little JavaScript embedded) |
| `car.svg` | Small local picture used for the blur examples |

## Background
Old Internet Explorer had a proprietary filter:
```css
filter: progid:DXImageTransform.Microsoft.MotionBlur(direction=90, strength=10);
```
`direction` is an angle (0 = up, 90 = right, 180 = down, 270 = left) and `strength` is the
blur length in pixels. Modern browsers do not support it, so the page emulates it with:
1. **SVG `<feGaussianBlur stdDeviation="x y">`** applied with `filter: url(#id)` - a blur on only
   one axis gives a horizontal or vertical motion blur; diagonal blur uses offset copies + blur.
2. **`text-shadow`** with several offsets in one direction - motion trail on text.
3. **`filter: blur(px)`** - uniform blur, shown for comparison.

## How to run
Keep both files in the same folder and open `index.html` in a modern browser
(Chrome, Firefox, Edge).

## Sample output
- A grid of the car picture: original, horizontal blur with strength 3 / 8 / 15, vertical blur,
  diagonal blur at 45 and 135 degrees, and a uniform blur.
- Text lines blurred to the right (weak and strong), left, up, down, diagonally.
- Section 3: pick a direction and drag the strength slider; the picture updates live.
