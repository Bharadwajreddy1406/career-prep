Imagine you're a top-secret agent receiving an encrypted directive from headquarters. The message comes as a string of digits, and each digit (from 2 to 9) is a cipher for a set of potential code letters. To uncover the true instruction, you must translate the string into every possible combination of letters by substituting each digit with its corresponding set of letters. The final decoded messages listed in lexicographical order.

Below is the mapping of digits to letters (as found on a traditional telephone keypad):

| Digit | Letters       |
|-------|---------------|
| 2     | a, b, c       |
| 3     | d, e, f       |
| 4     | g, h, i       |
| 5     | j, k, l       |
| 6     | m, n, o       |
| 7     | p, q, r, s    |
| 8     | t, u, v       |
| 9     | w, x, y, z    |

Note: The digit 1 does not correspond to any letters.

Example 1:
Input: 23  
Output: `[ad, ae, af, bd, be, bf, cd, ce, cf]`

Example 2:
Input: 2 
Output: `[a, b, c]`


Constraints:

- 0 <= digits.length <= 4  
- Each digit in the input is between '2' and '9'.

