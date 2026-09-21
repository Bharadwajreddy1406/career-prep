
---

Given an integer array `nums` and an integer `k`, return _the_ `k` _most frequent elements_. You may return the answer in **any order**.

**Example 1:**

**Input:** nums = [1,1,1,2,2,3], k = 2

**Output:** [1,2]

**Example 2:**

**Input:** nums = [1], k = 1

**Output:** [1]

**Example 3:**

**Input:** nums = [1,2,1,2,1,2,3,1,3,2], k = 2

**Output:** [1,2]

**Constraints:**

- `1 <= nums.length <= 105`
- `-104 <= nums[i] <= 104`
- `k` is in the range `[1, the number of unique elements in the array]`.
- It is **guaranteed** that the answer is **unique**.

**Follow up:** Your algorithm's time complexity must be better than `O(n log n)`, where n is the array's size.


---


took from a comment in discussion

```
I misunderstood the question for a long time too. So the idea of the question is to find out the K most frequent items and not "Which element is K times repeated". So when the test case is [1,2] and k being 2, you need to return 2 most frequent elements which is apparently 1,2 and the occurrences of each element doesn't matter as long as the order of elements to be printed is important . Lets take two cases here  
Case 1. Input : [1,1,2,3] and k = 2 , so 1 has occurred 2 times , 2 has occurred 1 time and 3 has occurred 1 time. So the answer would be [1,2].

Case 2: Input : [1,1,2,2,3] and K = 2, so 1 has occurred 2 times , 2 has occurred 2 times and 3 has occurred 1 time. So the answer is [1,2] and if the order of printing is important then first 1 and then 2 should be printed. (bcoz 1<2).

(Ultimately choose the first K elements when sorted in descending order of their occurrences.)

Look at this question : [https://leetcode.com/problems/top-k-frequent-words/](https://leetcode.com/link/?target=https%3A%2F%2Fleetcode.com%2Fproblems%2Ftop-k-frequent-words%2F)

This is of the similar type and would help getting a better idea of both the questions .

Idea to approach for both the questions:

1. Create a map, with the value and occurrences as key, value pair
2. Somehow sort according to the descending order of their occurrences and also include these cases, if you wish to  
    Case1. if two elements have same occurrences print the elements lexicographically(if strings) or ascending order of the numbers(if integers)  
    Case 2: if the occurrences are distinct, print in descending order of the occurrences of the elements.
3. Return the first K elements.
```

---
Solution :

```python
class Solution(object):

	def topKFrequent(self, nums, k):
	
		"""
		
		:type nums: List[int]
		
		:type k: int
		
		:rtype: List[int]
		
		"""
		
		  
		
		freq = {}
		
		ans = []
		
		for i in nums:
		
			freq[i] = freq.get(i, 0) + 1
		
		  
		
		res = sorted(freq.items(), key=lambda x: x[1], reverse=True)
		
		for i in range(k):
		
			ans.append(res[i][0])
		
		  
		
		return ans
```