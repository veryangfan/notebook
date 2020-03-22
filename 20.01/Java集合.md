# Java集合

## 分类
集合类主要分为两大类，即Collection和Map;  
Collection又分为List、Set和Queue;

![集合](https://note.obs.cn-north-4.myhuaweicloud.com/%E9%9B%86%E5%90%88.jpg)

---

## 实现原理

## List接口

### ArrayList

- ArrayList底层采用的是动态数组object[]进行实现的，数组可以动态扩容  
- 在无参构造的条件下，初始大小默认为10,每次扩容为1.5倍。即第一次为10，扩容后数组长度为15  
- 每次进行扩容后，都要将原数组中的元素拷贝(Arrays.copyof())到新数组中  
特点：读取快，插入删除慢


![arraylist](https://note.obs.cn-north-4.myhuaweicloud.com/arraylist.png)

### Vector
- Vector是线程安全的，与ArrayList类似，实际上已经废弃了。在Java1.0时还没有JUC包，保证线程安全的方法就是加锁，在方法、代码块声明时加入synchronized关键字修饰
- Vector扩容为2倍

### LinkedList
- LinkedList底层是基于双向链表实现的，不用进行扩容，可以一直追加
- LinkedList 还可以用作栈、队列和双向队列
```java
Queue<Integer> queue = new LinkedList<>();
queue.add(1);
queue.peek();
queue.poll();
```

特点：添加删除快，读取指定位置元素慢

![linkedlist](https://note.obs.cn-north-4.myhuaweicloud.com/linkedlist.jpg)

---
## Map接口

### HashMap
- HashMap底层存储使用的是数组形式，数组里面存储的是链表，这是为了采用拉链法解决哈希冲突
- 动态扩容，数组默认大小为16(必须为2的整数幂次，如果设置长度为12，也是16)，当达到容量的0.75倍时，进行扩容，将容量扩展为原来的2倍  
`put()`操作的时候，计算key的hashCode，并对其再次进行hash运算，使最后结果在[0,当前数组长度)范围，然后到数组这个位置的链表中遍历，看是否已经存在了这个key，如果确实没有则追加到链表尾部。  
`get()`操作的时候也类似，就是计算key的hash找到链表，遍历找到key，将Entry取出。
- 在Java1.8中，存储方式产生了变化，当链表长度大于8时，改为红黑树存储

![hashmap](https://note.obs.cn-north-4.myhuaweicloud.com/hashmap.jpg)

#### 为什么数组长度必须为2的整数次幂
根据hash值得到数组下标，最简单的方式是`hashCode % length`,HashTable就是这么做的。但是取模算法计算速度较慢，所以HashMap中将算法优化为`hashCode & (length-1)`，当length取值为2的整数次幂时，即可得到一个符合条件的下标值，且计算速度更快。

```java
static int indexFor(int h, int length) {
    // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
    return h & (length-1);
}
```

### LinkedHashMap
- LinkedHashMap继承了HashMap,是其子类。二者的区别就是HashMap是无序的(和Set一样)，而LinkedHashMap是有序的
- LinkedHashMap有序的实现原理是增加了一个双向链表，记录插入节点的顺序。即LinkedHashMap相当于是`HashMap + 一个双向链表`

![linkedhashmap](https://note.obs.cn-north-4.myhuaweicloud.com/linkedhashmap.jpg)
> 上图中，黑色的表示HashMap,红色的表示双向链表


### TreeMap
- TreeMap的底层原理是红黑树，树的每一个节点都是Entry<key,value>，根据key进行排序


### HashTable & ConcurrentHashMap
- 并发安全的Map,HashTable已经废弃，需要需要时使用ConcurrentHashMap
- ConcurrentHashMap相比HashTable有哪些改进？  
HashTable在进行元素操作的时候，是将整个HashTable锁住，
而Concurrent则是将该Entry所在的链表锁住，即分段锁、粒度更细的锁。

---
## Set
- set主要实现为HashSet和TreeSet
- set实现原理就是Map中对应的key

---
## Comparable vs Comparator
两者都是接口，前者是类实现这个接口后（重写compareTo(T t)），就可以通过Arrays.sort或Collections.sort对这个类组成的数组或集合进行排序。

后者则是如果类本身没有实现Comparable，且不方便改类的时候，可以通过写一个比较器类实现Comparator（重写compare(T t1,T t2)），将这个比较器传入sort方法中，也可以完成排序。

Comparable相当于“内部比较器”，而Comparator相当于“外部比较器”。

```java
//自定义 用户类型 排序方式，按年龄
public class User implements Comparable<User>{
    private int age;
    private String name;

    //实现Comparable接口必须重写compareTo方法
    @Override
    public int compareTo(User user) {
        if(this.age==user.age){
            return 0;
        }else if(this.age>user.age){
            return 1;
        }else{
            return -1;
        }
    }

    //===getter and setter======
}

```


```java
//将用户列表list按年龄排序
List<User> list = new ArrayList<>();
Collections.sort(list, new Comparator<User>() {
    @Override
    public int compare(User user1, User user2) {
        if(user1.getAge() == user2.getAge()){
            return 0;
        }else if(user1.getAge() > user2.getAge()){
            return 1;
        }else{
            return -1;
        }
    }
});
```