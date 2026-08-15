
# 🧮 Maximum Items That Can Be Delivered into Building Rooms

## 📌 Problem Statement

A courier company has to deliver `N` items with different heights.  
All items must be delivered to **one** building only.

The building has `K` rooms inside, indexed from `0` to `K-1`, each with a different height.  
The rooms are connected in **a linear fashion**:
- Room `0` connects to Room `1`,
- Room `1` connects to Room `2`, and so on.

### Rules:
- Each room can hold **at most one** item.
- If an item's height is **greater than** a room's height, it **stops** at that room. So, **all items behind it are also blocked**.
- Items **can be rearranged** before delivery.

### Goal:
Determine the **maximum number of items** that can be successfully placed into the rooms.

---

## 🧾 Input Format:
```

Line 1: Two integers N and K — number of items and number of rooms  
Line 2: N space-separated integers — heights of the items  
Line 3: K space-separated integers — heights of the rooms

```

---

## 📤 Output Format:
```

Print a single integer: the maximum number of items that can be delivered.

```

---

## 🧪 Sample Input-1:
```

4 5  
4 3 4 1  
5 3 3 4 1

```

### ✅ Sample Output-1:
```

3

```

### 💡 Explanation:
- Room heights from entry point onward are: [5, 3, 3, 4, 1]
- Construct minimum up to each room from entry to avoid blocked items: [5, 3, 3, 3, 1]
- Sorted items: [1, 3, 4, 4]
- Place 1 in room 4, 3 in room 2, 4 in room 0 → **3 items placed**

---

## 🧪 Sample Input-2:
```

5 4  
1 2 2 3 4  
3 4 1 2

```

### ✅ Sample Output-2:
```

3

````

---

## 💭 Concept

We want to simulate how many items can **pass through the rooms** and be placed:
- Items can only pass if all previous rooms have **greater than or equal height**.
- So for each room, we compute the **minimum height** from the start to that room.  
  This helps us know the **maximum height** an item can have to reach that room.

---

## 🧠 Approach

1. Build a `prefix min` array of room heights:
   - Each room should represent the **smallest height encountered so far** from room 0.
2. Sort the item heights in ascending order.
3. Try to place items from **the end of the rooms** (i.e., from the last room backward).
4. For each room (from end to start), if the current item fits (`item <= min_room_height`), we place it.
5. Move to the next item and repeat.

---

## 🧑‍💻 Code (Java)

```java
import java.util.*;

public class Solution{
    public static void main (String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int items[] = new int[n];
        int rooms[] = new int[k];
        
        for(int i=0; i<n; i++) items[i] = sc.nextInt();

        // Track prefix min of room heights
        int max = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<k; i++){
            rooms[i] = sc.nextInt();
            max = Math.min(max, rooms[i]);
            list.add(max); // prefix minimums
        }

        Arrays.sort(items); // sort items in increasing order
        
        int count = 0;
        int index = 0;

        // Start from last room and try to fit items
        for(int i = k - 1; i >= 0; i--){
            if(index < n && list.get(i) >= items[index]){
                count++;
                index++;
            }
        }

        System.out.print(count);
    }
}
````

---

## ✅ Output

Prints the **maximum number of items** that can be placed into rooms.

---

## 🧠 Complexity

- Time: `O(N log N + K)` for sorting and processing
    
- Space: `O(K)` for prefix min list
    

---

## 🧩 Tags

`Greedy` `Two Pointers` `Prefix Min Array` `Sorting` `Room Constraints`