

 ## 🕵️ Problem Statement

A detective is investigating a case involving a secret message hidden within a longer note. The culprit rearranged the letters of a **short code-word** in different parts of a longer **note**.

Your task is to **find all starting positions** in the `note` where any **rearrangement (anagram)** of `codeWord` exists as a substring.

---

## 🧾 Input Format

- A single line with two space-separated strings:  
  `note codeWord`

---

## 🖨️ Output Format

- A list of **integer indices** — starting positions in `note` where an anagram of `codeWord` begins.

---

## 📥 Sample Input-1

```

bacdgabcda abcd

```

### 📤 Sample Output-1

```

[0, 5, 6]

```

---

## 📥 Sample Input-2

```

bacacbacdcab cab

```

### 📤 Sample Output-2

```

[0, 3, 4, 5, 9]

````

---

## 🧠 Approach

- Use **Sliding Window** + **Frequency Arrays (size 26)**.
- Slide a window of size equal to `codeWord.length()` across `note`.
- Compare character frequency of each window with that of `codeWord`.

---

## 👨‍💻 Java Code

```java
import java.util.*;

public class Solution {
    public static List<Integer> findAnagramPositions(String note, String codeWord) {
        List<Integer> result = new ArrayList<>();
        if (note.length() < codeWord.length()) return result;

        int[] codeFreq = new int[26];
        int[] windowFreq = new int[26];

        for (int i = 0; i < codeWord.length(); i++) {
            codeFreq[codeWord.charAt(i) - 'a']++;
            windowFreq[note.charAt(i) - 'a']++;
        }

        if (Arrays.equals(codeFreq, windowFreq)) result.add(0);

        for (int i = codeWord.length(); i < note.length(); i++) {
            windowFreq[note.charAt(i) - 'a']++;
            windowFreq[note.charAt(i - codeWord.length()) - 'a']--;
            if (Arrays.equals(codeFreq, windowFreq)) result.add(i - codeWord.length() + 1);
        }

        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String note = sc.next(), codeWord = sc.next();
        List<Integer> positions = findAnagramPositions(note, codeWord);
        System.out.println(positions);
    }
}
````

---
