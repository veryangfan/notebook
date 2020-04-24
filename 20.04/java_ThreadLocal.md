# ThreadLocal

ThreadLocal，泛型类。顾名思义，线程本地变量，其在定义一个全局变量时，不同的线程可以get获取不同的结果。

## 内存结构

![threadlocal](https://bolg.obs.cn-north-1.myhuaweicloud.com/1912/ThreadLocal1.png)

原理：`ThreadLocal`对象本身其实不存储内容，而是`Thread`对象有个`ThreadLocalMap`来进行存储，`ThreadLocal`作为这个`map`的`key`，运行`get`方法，实际是`map.get(threadlocal)`

`ThreadLocalMap`是采用了数组的存储结构，每个元素就是entry本身。


注意：线程池与`ThreadLocal`配合使用的时候，一定注意线程是回收的，`Thread`对象还在，因而`ThreadLocalMap`还在，因而里面的`key-value`都没有被销毁。这样轻则是引起内存泄漏，一直没有清理这部分内容。严重的则会因为再次`get`到线程上一世的内容，导致错误。

所以如果`threadlocal`不再使用了，一定记得及时`remove`掉，防止不必要的麻烦。


## 常用方法get()，set()，remove()

源码阅读待更新 ing....