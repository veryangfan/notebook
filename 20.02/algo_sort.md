> 准备重新学习一下基础部分的数据结构，这次准备从后往前学
## 1. 快速排序
```java
import java.util.Arrays;

public class QuickSort{
    public static void quickSort(int[] arr,int left,int right) {
        if(left > right || arr.length < 2)
            return;
        int base = arr[left]; //首先选择左边作为基准数
        int i = left;
        int j = right;

        while(i<j){
            while(i<j && arr[j]>=base) //从右边开始，在右面找到比基准数小的就停下
                j--;
            while(i<j && arr[i]<=base) //再从左边找，在左面找到比基准数大的就停下
                i++;
            swap(arr,i,j); //交换这两个值
            //然后继续找，直到i和j相遇
        }
        swap(arr,left,i); //交换base与相遇的位置（i=j）的值
        //交换后以新base为中间值，划分成两个数组。新base左边<=base；新base右边>=base；
        quickSort(arr, left, i-1); //左右两边分别递归
        quickSort(arr, i+1, right); //重复这个过程
    }

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
    }
    
    //for test
    public static void main(String[] args) {
        int[] arr = {5,3,4,6,2,8,1,9,7};
        quickSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

}
```

## 2. 堆排序

### 堆

- 堆：堆通常是一个可以被看做一棵完全二叉树的数组对象。

- 堆总是满足下列性质：

    - 1.堆中某个节点的值总是不大于或不小于其父节点的值；

    - 2.堆总是一棵完全二叉树。

- 将根节点最大的堆叫做最大堆或大根堆，根节点最小的堆叫做最小堆或小根堆。

### 堆排序
```
1)构建堆，通过结点交换，实现堆的构建
2)排序，移除堆顶元素->将剩下的元素重新构建堆->再移除堆顶元素->...
```

```java
import java.util.Arrays;

public class HeapSort{
    
    /**
     * parent = (i-1)/2
     * c1 = 2*i+1
     * c2 = 2*i+2
     * 
     * @param tree 排序数组，表示堆
     * @param n 堆的节点数
     * @param i 操作的节点编号，从0开始（根节点序号为0）
     */
    private static void heapify(int[] tree,int n,int i){
        if(i >= n){
            return;
        }
        int c1 = 2 * i + 1;
        int c2 = 2 * i + 2;
        int max = i;
        if(c1 < n && tree[c1] > tree[max])
            max = c1;
        if(c2 < n && tree[c2] > tree[max])
            max = c2;
        if(max != i){
            swap(tree,max,i);   //找到最大值，和根节点进行交换
            heapify(tree, n, max); //调整完上层节点，下层堆顺序肯定也要变啊，所以还得重新调整
        }
    }

    /**
     * 从最后一个结点的父节点开始，倒着做heapify，一致做到堆顶，自下而上构建堆
     * @param tree
     * @param n
     */
    private static void buildHeap(int[] tree,int n){
        int last_node = n - 1;
        int parent = (last_node - 1)/2;
        for (int i = parent; i >= 0; i--) {
            heapify(tree, n, i); 
        }
    }
    
    /**
     * 每次都用第一个结点（最大值）和最后一个结点交换，
     * 然后去掉交换后的最后一个结点（最大值），重新构建大顶堆，再与新堆的最后一个交换
     * @param tree
     * @param n
     */
    public static void heapSort(int[] tree,int n){
        buildHeap(tree, n);
        for (int i = n - 1; i >= 0 ; i--) {
            swap(tree, i, 0);
            heapify(tree, i, 0); //这里设计成i，因为i一直在递减，就相当于砍掉了最后一个结点（所以最大值是数组最大下标）
        }
    }

	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
    }

    //for test
    public static void main(String[] args) {
        // int[] tree = {4,10,3,5,1,2};
        int[] tree = {4,10,2,5,1,3};

        // int[] tree = {2,5,3,1,10,4,8,6,7,9};
        // heapify(tree, tree.length, 0);
        // buildHeap(tree, tree.length);
        heapSort(tree, tree.length);
        System.out.println(Arrays.toString(tree));    
    }
}
```
