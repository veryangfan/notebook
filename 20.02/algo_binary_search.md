# 二分查找
- 记录一下
```java
class BinarySearch{
    public static boolean bs(int[] arr,int key){
        int low = 0;
        int high = arr.length - 1;

        if(key < arr[low] || key > arr[high] || low > high) return false;

        for (int i = 0; i < arr.length; i++) {
            int mid = (low + high) / 2;
            if(arr[mid] == key){
                return true;
            }else if(arr[mid] < key){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return false;
    }
    //for test
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        int key = 9;
        System.out.println(bs(arr, key)); 
    }
}
```