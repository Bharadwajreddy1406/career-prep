Imagine you're designing a sequence of signals for a high-tech robot. 
The robot receives its instructions as list of integers, where each integer 
represents one byte of the command data. A complete command can be composed of 
1 to 4 bytes, following these strict rules:

- For a 1-byte command, the first bit must be 0, followed by the command's code.
- For a multi-byte command (with n bytes), the first byte starts with n 
  consecutive 1’s, immediately followed by a 0. Each of the following n – 1 bytes 
  must begin with the bit pattern 10.

This is how the robot interprets the byte sequences:

 Number of Bytes   |        Robot Signal Sequence
				   |              (binary)
-------------------+---------------------------------------
		1          |   0xxxxxxx
		2          |   110xxxxx 10xxxxxx
		3          |   1110xxxx 10xxxxxx 10xxxxxx
		4          |   11110xxx 10xxxxxx 10xxxxxx 10xxxxxx

Here, each x represents a bit that can be either 0 or 1.

Note: Only the least significant 8 bits of each integer in the input list of 
integers are used, meaning each integer stands for exactly one byte of data.

Your challenge is to verify whether the provided sequence of robot instructions 
strictly follows the defined encoding rules.

Example 1:
----------
Input=
197 130 1

Output=
true

Explanation: 
- The array corresponds to the binary sequence: 11000101 10000010 00000001.  
- This is a valid encoding: a 2-byte command (11000101 10000010) followed by a 
  valid 1-byte command (00000001).

Example 2:
----------
Input=
234 140 4

Output=
false

Explanation:
- The array corresponds to the binary sequence: 11101011 10001100 00000100.  
- The first three bits of the first byte are 1’s with a following 0, indicating 
  a 3-byte command. The second byte starts correctly with 10, but the third byte 
  does not begin with 10, so the command sequence is invalid.

Constraints:

- 1 <= instructions.length <= 2 * 10^4
- 0 <= instruction <= 255



## Solution

```java
import java.util.Scanner;

public class Solution {
    public static boolean isValidEncoding(int[] instructions) {
        int count = 0;
        for (int b : instructions) {
            if (count == 0) {
                if ((b >> 7) == 0) continue;
                else if ((b >> 5) == 0b110) count = 1;
                else if ((b >> 4) == 0b1110) count = 2;
                else if ((b >> 3) == 0b11110) count = 3;
                else return false;
            } else {
                if ((b >> 6) != 0b10) return false;
                count--;
            }
        }
        return count == 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // Read the number of elements
        int[] instructions = new int[n];

        for (int i = 0; i < n; i++) {
            instructions[i] = sc.nextInt();
        }

        System.out.println(isValidEncoding(instructions));
        sc.close();
    }
}

```

