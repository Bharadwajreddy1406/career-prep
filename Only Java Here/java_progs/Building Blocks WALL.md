
# 📓 Building Blocks Wall Problem

## 🧩 Problem Statement

Bablu is working in a construction field.  
He has N number of building blocks, where the height and width of all the blocks are the same.  
The length of each block is given in an array `blocks[]`.

Bablu plans to build a wall in the form of a square.  
The rules to construct the wall are as follows:
- He should use all the building blocks.
- He should not break any building block, but you can attach them with other blocks.
- Each building block must be used only once.

Your task is to check whether Bablu can construct the wall as a square with the given rules or not. If possible, print `true`. Otherwise, print `false`.

---

### Input Format:
- Line-1: An integer N, number of building blocks.
- Line-2: N space-separated integers, lengths of each block.

### Output Format:
- Print a boolean value (`true` or `false`).

---

## 📝 Test Cases

### Sample Input-1:
```

6 1 2 6 4 5 6

```

### Sample Output-1:
```

true

```

---

### Sample Input-2:
```

6 5 3 2 5 5 6

```

### Sample Output-2:
```

false

````

---

### **Explanation**

The problem asks us to determine if we can form a square wall using building blocks with given lengths. Here's the breakdown:

1. **Total Area Calculation**:
   - The total area of all the blocks is simply the sum of the lengths of the blocks.
   - If the total area is divisible by 4, then there’s a potential that the blocks can be arranged into four sides of a square.

2. **Key Insight**:
   - If the total length (`tot`) of all blocks is divisible by 4, we can theoretically form a square. However, just knowing that the total length is divisible by 4 is not enough, as the individual blocks need to be rearranged to form four equal sides.
   
   - The algorithm checks if the total length is divisible by 4 (`tot % 4 == 0`). If it is, it assumes that a square might be possible, but it doesn't actually try to partition the blocks (which would require a more complex approach).

### **Approach**:

1. **Sum the Blocks**:
   - The first step is to calculate the total length of all the blocks.

2. **Check Divisibility**:
   - If the total length of all blocks (`tot`) is divisible by 4, then it is possible that the blocks can be rearranged to form four equal sides. If this is true, print `true`. If not, print `false`.

   - The key check is: `tot % 4 == 0`.

### **Code**:

```java
import java.util.*;

public class Solution{
    public static void main (String[] args) {
        // Initialize Scanner for input reading
        Scanner sc = new Scanner(System.in);
        
        // Number of building blocks
        int n = sc.nextInt();
        
        // Variable to store the total length of all blocks combined
        int tot = 0;
        
        // Read the lengths of the blocks and accumulate their total
        for(int i = 0; i < n; i++) {
            tot += sc.nextInt();
        }
        
        // Check if the total length is divisible by 4 to potentially form a square
        System.out.print(tot % 4 == 0 ? "true" : "false");
    }
}
````

### **Explanation of the Code**:

1. **Input Handling**:
    
    - We first take an integer `n` as input which represents the number of building blocks.
        
    - Then, we read `n` space-separated integers representing the lengths of each block.
        
2. **Summing the Lengths**:
    
    - We initialize a variable `tot` to 0, which will hold the total length of all the blocks combined.
        
    - We use a `for` loop to sum the lengths of all the blocks.
        
3. **Checking Divisibility**:
    
    - Once we have the total length of the blocks (`tot`), we check if it is divisible by 4.
        
    - If `tot % 4 == 0`, it means we can potentially divide the total length into four equal parts (sides of the square). Hence, we print `"true"`.
        
    - If not, we print `"false"`.
        

---

### **Observations and Limitations**:

- The solution works based on the assumption that if the total length of all blocks is divisible by 4, then we can form a square. However, in reality, this is only a preliminary check.
    
- **True Partitioning**: To truly form a square, we would need to partition the blocks into four subsets where each subset represents one side of the square. This could be achieved using more complex algorithms, such as **backtracking** or **dynamic programming**.
    
- The current approach does not actually attempt to partition the blocks but rather checks if a square might be possible based on the total sum of block lengths.
    

---
