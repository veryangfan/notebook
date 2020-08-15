# 锁 & 同步
Java提供了两种类型的锁，来控制多个线程对于共享资源的访问。
1. 一个是 JVM 实现的, `synchronized`，
2. 另一个是 JDK 实现的（AQS）,Lock接口的具体实现 `ReentrantLock`


## synchonized 同步关键字
将对象作为锁，对象头里面具有锁的标志位

### 用法
1）用于同步一个代码块，制定一个加锁的对象，比如给当前对象加锁
```java
public void func() {
    synchronized (this) {
        // ...
    }
}  
```
2）用于同步一个普通方法
```java
public synchronized void func () {
    // ...
}
```
3）用于同步一个静态方法
```java
public synchronized static void fun() {
    // ...
}
```  
4）用于同步一个类，给当前类的Class对象加锁
```java
public void func() {
    synchronized (SynchronizedExample.class) {
        // ...
    }
}
```  
1和2其实没有本质区别，都是只能作用于同一个对象  
3是作用于整个类，可以作用于多个对象，方法不管有多少个类实例，同时只有一个线程能获取锁进入这个方法。  
4因为将整个类都同步了，所以可以作用于多个对象

总结一下：也就是说1和2是只作用于同一个对象的，如果两个线程调用两个对象上的同步代码块，就不会进行同步。  
而3和4是作用在类上的，也就是说两个线程调用同一个类的不同对象上的这种同步语句，也会进行同步。
我们考虑单例模式的问题：这里就需要同步整个`Singleton`类，因为两个线程获取两个对象时，也需要进行同步竞争锁，从而才能保证返回一个单例对象。
```java
//单例模式
class Singleton{
	private volatile static final Singleton instance;
	private Singleton(){};
	public Singleton getInstance(){
		if(instance == null){
			synchronized(Singleton.class){
				if(instance == null){
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
}
```

### synchronized锁的升级
JDK1.6 之后对synchronized做了一些优化，为了减少获得锁和释放锁带来的性能开销，引入了`偏向锁`、`轻量级锁`的概念。因此在 synchronized 中，锁存在四种状态 分别是：无锁-->偏向锁-->轻量级锁-->重量级锁； 锁的状态根据竞争激烈的程度从低到高不断升级。

- 偏向锁：Thread1访问临界区后， 若接下来没有其他线程进入临界区，当Thread1再次进入临界区，临界区无需再执行任何同步操作

- 轻量级锁：Thread1访问临界区后，Thread2也进入临界区，但是 Thread1还没有执行完同步代码块时，会暂停Thread1并且升级到轻量级锁。所谓轻量级锁，就是此时Thread2不处于阻塞态，而是进行自旋CAS操作。

- 如果 Thread1和Thread2正常交替执行，那么轻量级锁基本能够满足锁的需求。但是如果Thread1和Thread2同时进入临界区，那么轻量级锁就会膨胀为重量级锁。意味着Thread1线程获得了重量级锁的情况下，Thread2就会被阻塞

有这样一个同步代码块，存在 Thread1、Thread2...等多个线程
情况一：只有 Thread1 会进入临界区；--> 偏向锁
情况二：Thread1和Thread2交替进入临界区,竞争不激烈；-->轻量级锁
情况三：Thread1/Thread2/Thread3… 同时进入临界区，竞争激烈。-->重量级锁



[参考博客](https://www.cnblogs.com/snow-man/p/10874464.html)

## ReentrantLock
Lock接口的具体实现类  
ReentrantLock是可重入的独占锁

两者比较：
1. synchonized是关键字，而ReentrantLock是类；
2. synchronized 是 JVM 实现的，而 ReentrantLock 是 JDK 实现的。
3. synchronized 中的锁是非公平的，ReentrantLock 默认情况下也是非公平的，但是也可以是公平的。
4. Lock实际上有一些高级的性能，比如tryLock（避免线程阻塞），可以绑定多个Condition对象


## ReadWriteLock读写锁
ReadWriteLock是一个读写锁的接口，ReentrantReadWriteLock是其一个具体实现，实现了读写的分离，**读锁是共享的，写锁是独占的**，这样读与读之间就不会发生互斥，读写、写读、写写之间才会发生互斥。


# 各种类型锁的定义
## 1.乐观锁与悲观锁
悲观锁认为数据很容易被其他线程修改，所以在数据整个过程中进行加锁，使数据处于阻塞状态。  
乐观锁认为数据在一般情况下不会产生冲突，所以在记录前不会加排他锁，而是在数据提交更新时，检测数据是否产生了变化。如果产生了变化，则重新获取再更新。

## 2.公平锁与非公平锁
根据抢占机制划分。公平锁是先到先得；非公平锁则是根据线程调度策略，先到不一定先得。
在没有公平需求的情况下，尽量使用非公平锁，公平锁会增大开销。

```java
//ReentrantLock提供了公平锁与非公平锁的实现

ReentrantLock lock = new ReentrantLock(true); //公平

ReentrantLock lock = new ReentrantLock(false); //非公平，默认
```
## 3.独占锁与共享锁
独占锁任何时候都保证只有一个线程可以得到，如ReentrantLock，ReadWriteLock的写锁  
共享锁可以同时由多个线程持有，ReadWriteLock的读锁  

## 4.可重入锁
当一个线程再次获取它已经获得的锁，如果不被阻塞，则可以说是可重入锁。
synchonized和ReentrantLock都是可重入锁。可重入锁的原理是在锁的内部维护了一个线程标志位--标志当前锁被哪个线程获取的，然后再关联一个计数器。当锁被获取时，计数器+1。此时另一个线程来获取锁，发现拥有者不是自己则阻塞；当原来的线程再次获取锁时，发现拥有者是自己，则计数器再+1，释放锁后，则计数器-1。当计数器为0时，线程标志位置为null，这是被阻塞的线程就回来竞争锁。

## 5. 自旋锁

线程自己旋转重复多次尝试获取该锁。

当前线程在获取锁时，发现锁已经被其他线程占有，并不马上阻塞自己，而是多次尝试获取，达到一次失败次数（通常是10次）后，再阻塞挂起。


#  死锁

死锁是指多个线程之间，因资源争夺，相互等待而无法继续运行下去的现象。

如：线程1持有资源1，还想持有资源2才能继续运行；而线程2持有资源2，还想持有资源1才能继续运行；这时候就会导致死锁

解决死锁的办法有很多，但原理都是破坏请求并持有条件和环路等待条件。  
(1) 预防死锁，考虑不同线程间获取锁的顺序问题。比如上面的问题改成线程1先请求资源1，在请求资源2；线程2也是先请求资源1，在请求资源2；即可避免死锁。

(2) 超时放弃，线程在阻塞一定时间后，主动放弃当前持有的锁，这样就可以打破死锁。例如Lock接口提供了boolean tryLock(long time, TimeUnit unit) throws InterruptedException方法，该方法可以按照固定时长等待锁。