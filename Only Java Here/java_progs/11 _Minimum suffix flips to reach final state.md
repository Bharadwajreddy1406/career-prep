## 🔄 Minimum Suffix Flip Operations to Reach Final State

`#greedy` `#binary-strings` `#bit-manipulation` `#java`

#### 📌 Problem Statement

Umesh is a mathematician.  
He gave a task to his student Shanker:

There are `N` coins in a row (indexed `0` to `N-1`) and all coins initially show **tail** (`0`).  
Shanker is given a **target state** in the form of a binary string `S` (with `'0'` for tail and `'1'` for head).

Shanker can perform **flip operations** as follows:

- Choose any index `i`.
    
- Flip all coins from index `i` to the end (`N-1`).
    
    - That means: `0 ⟷ 1` for all coins from `i` onward.
        

Help Shanker find the **minimum number of such suffix flip operations** required to achieve the final state.

**Input Format:**

```
A single binary string S
```

**Output Format:**

```
An integer representing the minimum number of flips
```

#### 🧪 Test Cases

```
Input:
10111010

Output:
6
```

```
Input:
11111

Output:
1
```

---

#### ✅ Code

```java
import java.util.*;

public class Solution {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        int state = 0;
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            if (state != s.charAt(i) - '0') {
                ans++;
                state = 1 - state;
            }
        }

        System.out.println(ans);
    }
}
```

---

#### 💡 Intuition & Explanation

- **Initial State:** All coins are `0` (tail).
    
- **Flip Operation:** Inverts all coins from index `i` to the end.
    

➡️ To minimize operations:

- Traverse left to right.
    
- Keep track of the current `state` of coins (`0` initially).
    
- If the current bit in `S` doesn't match `state`, it means we need to flip from here.
    
- After flipping, toggle `state` (0 → 1, or 1 → 0).
    
- Count the flips.
    

This greedy approach ensures each bit is made correct in the fewest operations possible.

---

#### 📊 Complexity

- Time Complexity: `O(N)`
    
- Space Complexity: `O(1)`
    

---
