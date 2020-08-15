# 设计模式
本文是对大话设计模式一书的笔记（持续更新，直到读完）

## 1. 简单工厂
简单工厂不属于23种设计模式，但是工厂模式和抽像工厂模式都是由此演化而来。**简单工厂就是由一个工厂类决定创建出哪一种具体实例**，而不像用户暴露具体细节。
```java
class ProductFactory{
    public static Product CreateProduct(String p){
        Product product;
        switch(p){
            case "A":
                product = new ProductA;
                break;
            case "B":
                product = new ProductB;
                break;
            case "C":
                product = new ProductC;
                break;
        }
        return product;
    }
}
```

## 2. 策略模式（Strategy）
策略一般指的是算法部分，将策略封装成一个成员变量放入执行类中，在不同的情况下注入不同的策略
核心思想：将策略（算法）抽象成一个接口，实现这个策略接口生成各种具体策略，从而注入不同的实现类。
应用场景：ribbon的负载均衡策略的替换（如随机，均匀）；JPA中@Id的生成策略（如递增）
```java
//策略Strategy API
public interface Stratery{
    public abstract void Algorithm();
}
```
```java
//具体策略 ConcreteStrategy
class ConcreteStrategyA implements Stratery{
    @override
    public void Algorithm(){
        System.out.println("algorithm A implements");
    }
}

class ConcreteStrategyB implements Stratery{
    @Override
    public void Algorithm(){
        System.out.println("algorithm B implements");
    }
}
```
```java
//上下文Context
class Context{
    Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void ContextAlgorithm(){
        strategy.Algorithm();
    }

}
```
```java
//客户端
Class client{
    public static void main(String[] args){
        Context context;

        context = new Context(Strategy StrategyA);
        context.ContextAlgorithm();
        
        context = new Context(Strategy StrategyB);
        context.ContextAlgorithm();
    }
}
```

## 3.代理模式

## 4.工厂模式 

## 5.单例模式 

1)饿汉式：不管你需不需要，new了再说
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

2)懒汉式：我比较懒，你需要我再new
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
3)静态内部类：静态内部类在JVM中是唯一的，可以保证单例对象的唯一性
```java
public class Singleton{
    private static class SingletonHolder{
        private static final Singleton INSTANCE = new Singleton;
    }
    private Singleton(){}
    public static final Singleton getInstance(){
        return SingletonHolder.Instance;
    }
}
```

4)双重校验锁: 双锁模式在懒汉式的基础上做了优化，给静态对象的定义加上volatile来保证初始化对象的唯一性，在获取对象时通过`syschronized(Singleton.class)`来给单例类加锁，保证操作的唯一性
```java
public class Singleton {
    //保证可见性和指令重排
    private volatile static Singleton instance; //1.对象锁
    //私有构造函数
    private Singleton(){}
    public static Singleton getInstance(){
        //第一重检查，可以避免每次调用都需要加锁
        if(instance == null){
            //同步锁定代码块
            synchronized (Singleton.class){ //2.synchronized方法锁
                //第二重检查
                if(instance == null){
                    //注意：非原子操作
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```


