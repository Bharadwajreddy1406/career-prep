
Imagine you’re managing a busy cafe where every order is logged. You have a 
record—a list of dish names ordered throughout the day—and you want to determine
which dishes are the most popular. Given an list of strings representing the dish
names and an integer P, your task is to return the P most frequently ordered dishes.

The results must be sorted by the number of orders, from the most frequent to 
the least. If two dishes have been ordered the same number of times, they should
be listed in alphabetical order.

Input Format:
-------------
Line-1: comma separated line of words, list of words.
Line-2: An integer P, number of words to display. 

Output Format:
--------------
Print P number of most common used words.

Example 1:
----------
Input=
coffee,sandwich,coffee,tea,sandwich,muffin
2
Output=
`[coffee, sandwich]`

Explanation: "coffee" and "sandwich" are the two most frequently ordered items. 
Although both appear frequently, "coffee" is placed before "sandwich" because 
it comes earlier alphabetically.

Example 2:
----------
Input=
bagel,muffin,scone,bagel,bagel,scone,scone,muffin,muffin
3
Output=
`[bagel, muffin, scone]` 

Explanation: "bagel", "muffin", and "scone" are the three most popular dishes 
with order frequencies of 3, 3, and 2 respectively. Since "bagel" and "muffin" 
have the same frequency, they are ordered alphabetically.

Constraints:

`- 1 ≤ orders.length ≤ 500  
`- 1 ≤ orders[i].length ≤ 10  
`- Each orders[i] consists of lowercase English letters.
`- P is in the range [1, The number of unique dish names in orders].`

---
## Solution

```java
import java.util.*;
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int count = 0;
    boolean isEnd = false;
}
class Trie {
    TrieNode root = new TrieNode();
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEnd = true;
        node.count++;
    }
    public int find(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) return 0;
            node = node.children[idx];
        }
        return node.isEnd ? node.count : 0;
    }
    public List<Pair> getWords() {
        List<Pair> list = new ArrayList<>();
        dfs(root, "", list);
        return list;
    }
    private void dfs(TrieNode node, String s, List<Pair> list) {
        if(node == null) return;
        if(node.isEnd) list.add(new Pair(s, node.count));
        for (int i = 0; i < 26; i++) {
            if(node.children[i] != null) dfs(node.children[i], s + (char)('a' + i), list);
        }
    }
}
class Pair {
    String word;
    int count;
    Pair(String word, int count) {
        this.word = word;
        this.count = count;
    }
}
public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] orders = sc.nextLine().split(",");
        int P = sc.nextInt();
        Trie trie = new Trie();
        for(String order : orders) {
            trie.insert(order.trim());
        }
        List<Pair> list = trie.getWords();
        Collections.sort(list, new Comparator<Pair>() {
            public int compare(Pair a, Pair b) {
                if(b.count != a.count) return b.count - a.count;
                return a.word.compareTo(b.word);
            }
        });
        List<String> res = new ArrayList<>();
        for (int i = 0; i < P && i < list.size(); i++) {
            res.add(list.get(i).word);
        }
        System.out.println(res);
    }
}
```
