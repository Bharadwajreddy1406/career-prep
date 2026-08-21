
For the given a list of integers, Your task to is find out, the length of the 
longest subsequence that is a toggle sequence.

Toggle Sequence means, the difference between the consecutive numbers
are alternate positive and negative.

For Example:
Given list of integers = 1 3 2 5 4 
the consecutive differences are `[ 2, -1, 3, -1]`, 
the differences are alternate +ve and -ve.
So, complete list is a toggle sequence.

If the list of integers = 1 3 2 4 5,
the consecutive differences are `[ 2, -1, 2, 1]`, not alternate +ve and -ve.
Not a toggle sequence.

Note: A sequence with fewer than two elements is a toggle sequence.

Input Format:
-------------
Space separated Integers, List

Output Format:
--------------
Print the length of the longest toggle sequence


Sample Input-1:
---------------
1 7 4 9 2 5

Sample Output-1:
----------------
6

Explanation:
------------
Given list of integers = 1 7 4 9 2 5
the consecutive differences are `[ 6, -3, 5, -7, 3], 
the differences are alternate +ve and -ve.
So, complete list is a toggle sequence.

Sample Input-2:
---------------
1 5 4 3 7 9 10

Sample Output-2:
----------------
4

Explanation:
------------
Given list of integers = 1 5 4 3 7 9 10
There are several subsequences that achieve this length.
One is `[1 5 4 7]` with differences `(4, -1, 3).`

---
# Solution

```java
import java.util.*;

public class Solution{
    
    
    public static int solve(int [] arr){
        if( arr.length < 2) return arr.length;
        
        Map<String,Integer> map = new  HashMap<>();
        return Math.max(helper(arr, 1, true ,map),helper(arr,1,false,map));
    }
    
    public static int helper(int [] arr, int index, boolean pos, Map<String,Integer> map){
        
        if( index == arr.length ) return 0;
        int p =0, np=0;
        String search = index +"-->"+pos;
        if(map.containsKey(search)) return map.get(search);
        
        if( pos && arr[index] > arr[index-1]  || !pos && arr[index] < arr[index -1]){
            p = 1 + helper(arr, index+1 , !pos,map);
        }
        
        np = helper(arr,index+1,pos,map);
        map.put(search,Math.max(p,np));
        return map.get(search);
        
    }
    
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String [] s= sc.nextLine().split(" ");
        int n = s.length;
        int [] arr = new int[n];
        for(int i=0;i<n;i++) arr[i] = Integer.parseInt(s[i]);
        System.out.println(solve(arr)+1);
        
    }
}
```