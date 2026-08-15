---
tags:
  - dsa
  - greedy
  - interval-scheduling
  - coding-question
date: 2025-04-10
title: 🎉 Telangana Youth Festival - Max Programs
---

  

## 🧩 Problem Statement

In Hyderabad after a long pandemic gap, the Telangana Youth festival is organized at HITEX. In HITEX, there are a lot of programs planned. During the festival in order to maintain the rules of Pandemic, they put a constraint that **one person can only attend any one of the programs in one day according to planned days**.

  

Now it’s your aim to implement the "Solution" class in such a way that you need to **return the maximum number of programs** you can attend according to given constraints.

  

You have a list of programs 'p' and days 'd', where you can attend only one program on one day. Programs [p] = [first day, last day], `p` is the program's first day and the last day.

  

---
## 📥 Input Format
  
- Line-1: An integer N, number of programs.

- Line-2: N comma separated pairs, each pair(f_day, l_day) is separated by space.
---
## 📤 Output Format

- An integer, the maximum number of programs you can attend.
---
## 📘 Sample Input-1:

```

4

1 2,2 4,2 3,2 2

```

  

## 📙 Sample Output-1:

```

4

```

  

---

  

## 📘 Sample Input-2:

```

6

1 5,2 3,2 4,2 2,3 4,3 5

```

  

## 📙 Sample Output-2:

```

5

```

  

---

  

## 🧠 Approach

  

This is a greedy interval scheduling problem. The goal is to attend the **maximum number of non-overlapping programs**, where overlapping is defined by attending two programs on the same day.

  

1. Sort all programs by their **ending day**.

2. For each program, greedily select the earliest available day in its interval that hasn’t already been taken.

3. Use a `Set` to keep track of already occupied days.

  

---

  

## 👨‍💻 Java Code

  

```java

import java.util.*;

  

public class Solution {

    public static int maxPrograms(int[][] programs) {

        Arrays.sort(programs, (a, b) -> a[1] - b[1]);

        Set<Integer> usedDays = new HashSet<>();

        int count = 0;

  

        for (int[] p : programs) {

            for (int day = p[0]; day <= p[1]; day++) {

                if (!usedDays.contains(day)) {

                    usedDays.add(day);

                    count++;

                    break;

                }

            }

        }

        return count;

    }

  

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        String[] input = sc.nextLine().split(",");

        int[][] programs = new int[n][2];

  

        for (int i = 0; i < n; i++) {

            String[] days = input[i].trim().split(" ");

            programs[i][0] = Integer.parseInt(days[0]);

            programs[i][1] = Integer.parseInt(days[1]);

        }

  

        System.out.println(maxPrograms(programs));

    }

}

  

```