# Top K问题
- 解决Top K问题最好的办法就是维护一个大小为K的大顶堆
```
1)将新加入的元素堆化
2)删除堆顶元素（即最小元素），剩下的k个元素就是TopK Elements 
```
- 在Java中，实现堆排序的集合是`PriorityQueue`,默认是一个最小堆，小的数在队列头，大的数在队列尾，所以无论何时调用`remove()`方法，删除的都是最小的数


```java
//LeetCode-215 Kth Element (数组中第K个最大元素)
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(); //小顶堆
    for (int val : nums) {
        pq.add(val);
        if (pq.size() > k)  //维护堆的大小为K
            pq.poll();
    }
    return pq.peek();
}
```

- 如果要解决Top K,稍加变化
```java
//TopK Elements 
private int[] topK(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(); //小顶堆
    for (int val : nums) {
        pq.add(val);
        if (pq.size() > k)  //维护堆的大小为K
            pq.poll();//删除最小的元素
    }
    //转化成数组
    int[] res = new int[k];
    for(int i = k-1;i>=0;i--){
        res[i] = pq.poll();
    }
    return res;
}
```
- `PriorityQueue`屏蔽了很多细节，比如K个元素的堆是如何维护的，这就涉及到堆排序相关知识
