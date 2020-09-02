# 02.单例模式的实现
1. 饿汉式：不管你需不需要，new了再说
缺点：浪费空间
```java
public class Singleton {  
    private static Singleton instance = new Singleton(); //直接new 
    private Singleton (){}  
    public static Singleton getInstance() {  
        return instance;  
    }  
}
```
2. 懒汉式：我比较懒，你需要我再new
缺点：每次调用`getInstance()`都需要加锁，影响性能
```java
//线程安全版本,加锁
public class Singleton {  
    private static Singleton instance;//只声明，不new  
    private Singleton (){}  
    public static synchronized Singleton getInstance() {  
        if (instance == null) {  
            instance = new Singleton();  
        }  
        return instance;  
    }  
}
```


3. 双重校验锁
```java
public class Singleton {
    private volatile static Singleton instance;
    private Singleton(){}
    public static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

---

# 03.找出数组中重复的数字。

在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
```
输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3 
```

【代码】
```java
//要求时间复杂度O(n),空间复杂度O(1)
class Solution {
    public int findRepeatNumber(int[] nums) {
        int i = 0;
        while(i < nums.length) {
            if(nums[i] == i) {
                i++;
                continue;
            }
            if(nums[nums[i]] == nums[i]) return nums[i];
            swap(nums,i,nums[i]);
        }
        return -1;
    }

    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
```

---

# 06. 从尾到头打印链表

输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

```
输入：head = [1,3,2]
输出：[2,3,1]
```

【代码】
```java
class Solution {
    private List<Integer> list = new ArrayList<>();
    public int[] reversePrint(ListNode head) {
        List<Integer> list = reverse(head);
        //存到数组里
        int[] res = new int[list.size()];
        for(int i = 0;i<res.length;i++){
            res[i] = list.get(i);
        }
        return res;
    }
    //递归实现
    private List<Integer> reverse(ListNode head){
        if(head != null){
            reversePrint(head.next);
            list.add(head.val);
        }
        return list;
    }
}
```
# 09.用两个栈实现一个队列。
队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )

示例 1：
```
输入：
["CQueue","appendTail","deleteHead","deleteHead"]
[[],[3],[],[]]
输出：[null,null,3,-1]
```
示例 2：
```
输入：
["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
[[],[],[5],[2],[],[]]
输出：[null,-1,null,null,5,2]
```

【代码】
```java
class CQueue {
    Stack<Integer> stackIn;
    Stack<Integer> stackOut;

    public CQueue() {
        stackIn = new Stack<>();
        stackOut = new Stack<>();
    }
    
    //添加，直接加到stackIn就行了
    public void appendTail(int value) {
        stackIn.push(value);
    }
    //删除，stackOut不空，删除；stackOut空，把stackIn的数据放进去，如果还是空，报错
    public int deleteHead() {
        if(!stackOut.isEmpty()){
            return stackOut.pop();
        }else{
            while(!stackIn.isEmpty()){
                int val = stackIn.pop();
                stackOut.push(val);
            }
            return stackOut.isEmpty() ? -1 : stackOut.pop();
        }
    }
}
```

# 10- I. 斐波那契数列

写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：
```
F(0) = 0,   F(1) = 1
F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
```
斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
示例 1：
```
输入：n = 2
输出：1
```
示例 2：
```
输入：n = 5
输出：5
```

【代码】

```java
class Solution {
    public int fib(int n) {
        if(n == 0) return 0;
        if(n <= 2) return 1;
        int first = 1;
        int second = 2;
        int nth = 2;
        for(int i = 3;i < n;i++){
            nth = (first + second) % 1000000007;
            first = second;
            second = nth;
        }
        return nth;
    }
}
```


# 10- II. 青蛙跳台阶问题

一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

示例 1：
```
输入：n = 2
输出：2
```
示例 2：
```
输入：n = 7
输出：21
```
示例 3：
```
输入：n = 0
输出：1
```
【代码】
```java
class Solution {
    public int numWays(int n) {
        if(n <= 1) return 1;
        int first = 1;
        int second = 2;
        int nth = 2;
        for(int i = 3;i <= n;i++){
            nth = (first + second) % 1000000007;
            first = second;
            second = nth;
        }
        return nth;
    }
}
```



