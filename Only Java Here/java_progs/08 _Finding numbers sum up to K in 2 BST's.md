

Imagine you are an explorer who stumbles upon two ancient treasure chests. 
However, these aren’t ordinary chests—the coins inside are arranged in a mysterious, 
branching order. In each chest, the coins are hidden in secret compartments where 
lower valued coins are tucked away on one side and higher valued coins are placed 
on the opposite side. To unlock the legendary vault of riches, you must select one 
coin from the first chest and one from the second chest such that their magical 
values add up to a secret key number. 
Your challenge is to return true if you can unlock the legendary vault of riches, 
otherwise false.

Example 1
----------
Input=
2 1 4
1 0 3
5

Output=
true

Chest A:
  2
   / \
  1   4

Chest B:
  1
   / \
  0   3

Explanation :Choosing the coin with value 2 from Chest A and the coin with value 3 
from Chest B adds up to 5, unlocking the vault

Example 2:
----------
Input=
0 -10 10
5 1 7 0 2
18

Output=
false

Chest A:
    0
   / \
-10   10

Chest B:
      5
     / \
    1   7
    / \
    0   2

Explanation: No combination of one coin from Chest A and one coin from Chest B 
sums to 18, so the vault remains sealed.



## Solution

```java

/*
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
*/
import java.util.*;
class Solution {
    
    public boolean search(TreeNode root, int key){
        if(root == null ) return false;
        
        if(root.val == key) return true;
        
        if(key < root.val){
            return search(root.left,key);
        }
        if(key > root.val){
            return search(root.right,key);
        }
        
        return false;
    }
    
    public boolean find(TreeNode root1, TreeNode root2, int target) {
    //write your code here
        if(root1 == null) return false;
        if(find(root1.left,root2,target) == true) return true;
        int k = target - root1.val;
        if(search(root2,k) == true){
            return true;
        }
        if(find(root1.right,root2,target) == true) return true;
        
        return false;
    
    }
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
    //write your code here
        if( root1.val == root2.val ) return true;
        return find(root1, root2, target);
    
    }
}
```