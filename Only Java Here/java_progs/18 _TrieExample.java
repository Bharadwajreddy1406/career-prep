import java.util.*;

class Node {

    Node[] children;
    boolean isEnd;

    Node() {

        children = new Node[26];
        isEnd = false;
    }

}

class Trie {

    Node root;
    int maxlen;

    Trie() {
        root = new Node();
        maxlen = 0;
    }

    public void insert(String word) {

        Node temp = root;
        for (int i = 0; i < word.length(); i++) {
            if (temp.children[word.charAt(i) - 'a'] == null) {
                temp.children[word.charAt(i) - 'a'] = new Node();
            }
            temp = temp.children[word.charAt(i) - 'a'];
        }
        if (temp.isEnd == true) {
            maxlen = Math.max(maxlen,word.length());
        } else {
            temp.isEnd = true;
        }
    }

    public boolean startsWith(String prefix) {

        Node temp = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (temp.children[prefix.charAt(i) - 'a'] == null) {
                return false;
            }
            temp = temp.children[prefix.charAt(i) - 'a'];
        }
        return true;
    }

    public boolean search(String word) {

        Node temp = root;
        for (int i = 0; i < word.length(); i++) {
            if (temp.children[word.charAt(i) - 'a'] == null) {
                return false;
            }
            temp = temp.children[word.charAt(i) - 'a'];
        }
        return temp.isEnd == true;
    }

    public String[] autoColmplete(String prefix) {

        Node temp = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (temp.children[prefix.charAt(i) - 'a'] == null) {
                return new String[0];
            }
            temp = temp.children[prefix.charAt(i) - 'a'];
        }
        List<String> result = new ArrayList<>();
        autoCompleteHelper(temp, prefix, result);
        return result.toArray(new String[0]);
    }

    private void autoCompleteHelper(Node node, String prefix, List<String> result) {
        if (node.isEnd) {
            result.add(prefix);
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                autoCompleteHelper(node.children[i], prefix + (char) (i + 'a'), result);
            }
        }
    }

}

public class TrieExample {

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Trie trie = new Trie();
        // trie.insert("apple");

        String word = "scarabhjbhjscarab";

        for (int i = 0; i < word.length(); i++) {
            for (int j = i + 1; j <= word.length(); j++) {
                String sub = word.substring(i, j);
                // System.out.println(sub);
                trie.insert(sub);
            }
        }

        System.out.println(trie.maxlen);
        // System.out.println(trie.startsWith("ball")); // true
        // System.out.println(trie.startsWith("app")); // true
        // System.out.println(trie.startsWith("appl")); // true
        // System.out.println(trie.startsWith("apples")); // false

    }
}


// # this is the code i have writtten chill dudes