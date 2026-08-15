Imagine you're managing a busy warehouse where every product is delivered in 
pairs to ensure proper stocking. However, due to a mix-up at the shipping dock, 
two unique product IDs ended up without their matching pair, while all other 
products arrived as complete pairs. Your task is to identify these two solitary 
product IDs.

You're given list of product IDs. In this list, every product ID appears exactly 
twice except for two IDs that appear only once. Return these two unique product 
\IDs in any order.

You must design an algorithm that runs in linear time and uses only constant 
extra space.


Example 1:
----------
Input: 
101 102 101 103 102 105  
Output: 
`[103, 105]` 
 
Explanation: The IDs 103 and 105 appear only once, while all other IDs appear 
twice. `[105, 103]` is also an acceptable answer.

Example 2:
-----------
Input: 121 136
Output: `[121, 136]`

---

## Solution

```java
import java.util.*;

public class Solution{
    
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] s = sc.nextLine().split(" ");
        int [] nums = new int[s.length];
        
        for(int i=0;i<s.length;i++){
            nums[i] = Integer.parseInt(s[i]);
        }
        
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<s.length;i++){
            map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            
            if(map.get(nums[i]) == 2) map.remove(nums[i]);
        }
        
        System.out.println(map.keySet());
    }
}
```

---
there's a more efficient approach, but will modify this later