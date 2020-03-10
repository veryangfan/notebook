# 设计模式
本文是对大话设计模式一书的笔记（持续更新，直到读完）

### 1. 简单工厂
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

### 2. 策略模式（Strategy）
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







