# 线程的三种创建方法  
1. 实现Runnanle接口，重写run方法
```java
//需要实现接口中的 run() 方法。

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        // ...
    }
}
```
```java
//使用 Runnable 实例再创建一个 Thread 实例，然后调用 Thread 实例的 start() 方法来启动线程。

public static void main(String[] args) {
    MyRunnable instance = new MyRunnable();
    Thread thread = new Thread(instance);
    thread.start();
}
```
2. 继承Thread类，并重写run方法
```java
//同样也是需要实现run() 方法，因为 Thread 类也实现了 Runable 接口。

//当调用 start() 方法启动一个线程时，虚拟机会将该线程放入就绪队列中等待被调度，当一个线程被调度时会执行该线程的 run() 方法。

public class MyThread extends Thread {
    @Override
    public void run() {
        // ...
    }
}
```
```java
public static void main(String[] args) {
    MyThread mt = new MyThread();
    mt.start();
}
```
3. 实现Callable接口
```java
//与 Runnable 相比，Callable 可以有返回值，返回值通过 FutureTask 进行封装。

public class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() {
        return 123;
    }
}
public static void main(String[] args) throws ExecutionException, InterruptedException {
    MyCallable mc = new MyCallable();
    FutureTask<Integer> ft = new FutureTask<>(mc);
    Thread thread = new Thread(ft);
    thread.start();
    System.out.println(ft.get());
}
```

# 线程池

为什么需要线程池呢？线程池主要作用是为了减少线程对象的创建(new)和销毁(GC)过程，这些操作会降低性能。

## 如何创建线程池
创建线程池可以使用`ThreadPoolExecutor`的构造函数直接创建
```java
/**
 *  corePoolSize    核心线程数
 *  maximumPoolSize 最大线程数
 *  keepAliveTime   idle线程存活时间
 *  unit            上个参数的单位
 *  workQueue       线程对象的缓冲队列
 *
 *---------------上面是重点---------------------
 *
 *  threadFactory   生成线程的工厂(可选)
 *  handler         达到容量后的回调(可选)
 */
public ThreadPoolExecutor(int corePoolSize,
                            int maximumPoolSize,
                            long keepAliveTime,
                            TimeUnit unit,
                            BlockingQueue<Runnable> workQueue,
                            ThreadFactory threadFactory
                            RejectedExecutionHandler handler)
```
核心线程数就是线程池的最佳工作状态的线程数，少于这个数量，则增加线程以趋近核心线程的数值。  
如果超过了核心线程数，且存在线程处于idel空闲状态，则会在
`keepAliveTime`时间后，gc掉这个空闲的线程。

具体过程：  
1. 线程池接到一个task，查看`corePoolSize`,若当前线程数少于这个线程，则new一个新的线程，完成这个task  
2. 直到当前线程数`=corePoolSize`,再接到新的task时，会将当前任务存储到`workQueue`中
3. 直到workQueue满了，再到来新的task时，此时会查看`maximumPoolSize`，若当前线程数`< maximumPoolSize`，则创建新线程处理这个新来的task（注意不是处理队列中的，而是处理新来的），来的早不如来得巧啊
4. task一直到来，直到当前线程数`= maximumPoolSize`时，此时触发rejectExecute饱和策略，回调handler，如抛出异常

上面的情况是指任务量较多的情况。通常如果中途线程执行完了一个task，就会从队列中拿task去做。如果队列中没有任务，那么当前线程对象就无所事事，idle状态。如果线程是idle状态持续keepalive时间了，且当前总线程数>coreSize，那么就gc掉这个线程对象，避免占用太多内存。

## jdk中的线程池类型 

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>());
}

public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                    60L, TimeUnit.SECONDS,
                                    new SynchronousQueue<Runnable>());
}

public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
}
```

- `newFixedThreadPool` : 创建一个核心线程数和最大线程数都为nThreads的线程池，阻塞队列长度为Integer.MAX_VALUE(LinkedBlockingQueue队列长度)，可以一直等待。keepAliveTime=0说明线程个数比核心线程数多则回收。
- `newCachedThreadPool`： 核心线程数为0，最大线程数为Integer.MAX_VALUE，阻塞队列长度为0（设计为同步队列），keepAliveTime=60说明空闲60s则回收。 这个线程池的特点在于，加入同步队列的任务，马上会被执行。
- `newSingleThreadExecutor`：核心线程数和最大线程数都为1，阻塞队列长度为Integer.MAX_VALUE。keepAliveTime=0说明线程个数比核心线程数1大，则回收。从而实现只保留一个线程。 






  

