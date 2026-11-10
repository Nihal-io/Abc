# 49 (a)(iv) – Simple calculator (PHP)

**Question:** A simple calculator application that takes two numbers and an operator
(+, -, *, /, %) from an HTML page and returns the result page with the operation performed on
the operands.

## Files
| File | Purpose |
|------|---------|
| `index.html` | Form with two number fields and an operator drop-down, POSTs to `calculate.php` |
| `calculate.php` | Validates the input, performs the operation with a `switch` and returns the result page |

## How to run
1. Put the folder in the web root or run `php -S localhost:8000` inside it.
2. Open `http://localhost:8000/index.html`.

## Sample input / output
| First | Operator | Second | Result page |
|-------|----------|--------|-------------|
| 12 | + | 8 | `12 + 8 = 20` |
| 7 | - | 10 | `7 - 10 = -3` |
| 2.5 | * | 4 | `2.5 * 4 = 10` |
| 10 | / | 4 | `10 / 4 = 2.5` |
| 17 | % | 5 | `17 % 5 = 2` |
| 5 | / | 0 | `Error: Division by zero is not allowed.` |
| 5 | % | 0 | `Error: Modulus by zero is not allowed.` |

## Notes
- `%` is calculated with `fmod()` so it also works for decimal numbers (e.g. `7.5 % 2 = 1.5`).
- Non-numeric input or an unknown operator gives an error message instead of a result.
