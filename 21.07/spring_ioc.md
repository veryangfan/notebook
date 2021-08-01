# Spring IoC

[toc]

主要记录一直以来都没学明白的一些问题

## 声明Bean

**Bean到底是什么?**

**其实并不需要理解Bean是什么**，只要知道它是Spring实现自动注入生成对象过程中，必须使用的一个**组件**，这样就可以了。Spring在实例化对象的时候，不止需要一个类，还需要将这个类配置为Bean才可以，通常情况下Spring容器中管理着许多Bean。

将类配置为Bean通常有三种方式: xml、注解、Java Bean
### 1. xml

xml是比较早的配置方式，其灵活且复杂，下面配置了两个Bean。

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans   xmlns="http://www.springframework.org/schema/beans" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans 
         http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
     <!--其中id是bean名称，class是bean名称-->
    <bean id="car"  class="com.yf.simple.Car"></bean>  
    <bean id="boss" class="com.yf.simple.Boss"></bean>
</beans>
```

### 2. 注解

>我们都在微博上@过某某，对方会优先看到这条信息，并给你反馈，那么在Spring中，你标识一个@符号，那么Spring就会来看看，并且从这里拿到一个Bean（注册）或者给出一个Bean（使用）

注解的配置方式是现在最主流的使用方式，SpringBoot项目通常也是这么使用。可以看到，使用`@Component`注解在`UserDao`类声明处对类进行标注，它可以被Spring容器识别，Spring容器自动将POJO转换为容器管理的Bean

```java
package com.yf.anno;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component("userDao")
public class UserDao {

}

```

这段代码等效于xml

```xml
<bean id="userDao" class="com.yf.anno.UserDao"/>
```

可以看出，注解的写法更容易理解。使用xml配置的方式，导致类的声明与Bean的声明过程是分开的；而使用注解的方式，只需要在类上加一个注解就可以同时将该类声明为Bean了。

除了`@Component`以外，Spring提供了3个功能基本和`@Component`等效的注解，它们分别用于对DAO、Service及Web层的Controller进行注解，所以也称这些注解为Bean的衍型注解，之所以要在@Component之外提供这三个特殊的注解，是为了让注解类本身的用途清晰化，此外Spring将赋予它们一些特殊的功能。

注解|说明
:-:|:-:
`@Component`|最普通的组件，可以被注入到spring容器进行管理
`@Repository`|作用于持久层，在MyBatis时，也可以使用`@Mapper`
`@Service`|作用于业务逻层
`@Controller`|作用于变现层（SpringMVC注解）

#### 命名空间
Spring提供了一个context的命名空间，它提供了通过扫描类包以应用注解定义Bean的方式，只有在命名空间内的类标注了相关注解，Spring才会将其扫描为Bean。命名空间可以自己配置。

- xml配置命名空间
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--①声明context的命名空间-->
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans 
         http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
         http://www.springframework.org/schema/context
         http://www.springframework.org/schema/context/spring-context-3.0.xsd"
         >
    <!--②扫描类包以应用注解定义的Bean-->
   <context:component-scan base-package="com.yf.anno"/>
        <bean class="com.yf.anno.LogonService"></bean>
   </context:component-scan -->
</beans>
```

- 注解配置命名空间
同样，我们也可以使用`@ComponentScan`注解声明

```java
@ComponentScan(scanBasePackages = "com.yf.anno.LogonService")
public class BeanTestApplication {
    public static void main(String[] args) {

    }
}
```

ps: SpringBoot默认会扫描基类包里的所有类

### 3. Java Bean

JavaBean的配置方式也是采用两个注解，即`@Configuration`和`@Bean`.


1. `@Configuration` 作用于类上，在普通的实体类中只要标注@Configuration注解，就可以为Spring容器提供Bean定义的信息
2. `@Bean` 作用于类方法，每个标注了@Bean的类方法都相当于提供了一个Bean的定义信息。

```java
package com.yf.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//①将一个POJO标注为定义Bean的配置类
@Configuration
public class AppConf {
    //②以下两个方法定义了两个Bean，以提供了Bean的实例化逻辑
    @Bean
    public UserDao userDao(){
       return new UserDao();    
    }
    
    @Bean
    public LogDao logDao(){
        return new LogDao();
    }
    //③定义了logonService的Bean
    @Bean
    public LogonService logonService(){
        LogonService logonService = new LogonService();
        //④ 将②和③处定义的Bean注入到LogonService Bean中
        logonService.setLogDao(logDao());
        logonService.setUserDao(userDao());
        return logonService;
    }
}
```

①处: 在AppConf类的定义处标注了@Configuration注解，说明这个类可用于为Spring提供Bean的定义信息。  
类的方法处可以标注@Bean注解，Bean的类型由方法返回值类型决定，名称默认和方法名相同，也可以通过入参显示指定Bean名称，如@Bean(name="userDao").直接在@Bean所标注的方法中提供Bean的实例化逻辑。

在②处userDao()和logDao()方法定义了一个UserDao和一个LogDao的Bean，它们的Bean名称分别是userDao和logDao。在③处，又定义了一个logonService的Bean，并且在④处注入②处所定义的两个Bean。

等效于xml声明了
```xml
<bean id="userDao" class="com.yf.anno.UserDao"/>
<bean id="logDao" class="com.yf.anno.LogDao"/>
<bean id="logService" class="com.yf.conf.LogonService" p:logDao-ref="logDao" p:userDao-ref="userDao"/>
```

## 注入

Spring注入就是将具体信息注入到Bean里，从而生成对象。

### 属性（property）注入

属性注入即通过setXxx()方法注入Bean的属性值或依赖对象，由于属性注入方式具有可选择性和灵活性高的优点，因此属性注入是实际应用中最常采用的注入方式。

属性注入要求Bean提供一个**默认的构造函数**，并为需要注入的属性提供对应的**Setter方法**。Spring先调用Bean的默认构造函数实例化Bean对象，然后通过反射的方式调用Setter方法注入属性值。

```xml
<bean id="myCar" class="cn.yf.pojo.Car">
    <!-- 通过constructor-arg的name属性，指定构造器参数的名称，为参数赋值 -->
    <constructor-arg name="speed" value="100" />
    <constructor-arg name="price" value="99999.9"/>
</bean>

<bean id="user" class="cn.yf.pojo.User">
    <property name="name" value="aaa" />
    <property name="age" value="123" />
    <!-- car是引用类型，所以这里使用ref为其注入值，注入的就是上面定义的myCar 
         基本数据类型或Java包装类型使用value，
         而引用类型使用ref，引用另外一个bean的id 
    -->
    <property name="car" ref="myCar" />
</bean>
```

### 构造方法（constructor）注入

使用构造方法注入的前提是Bean必须提供带参数的构造函数。

```xml
<bean id="myCar" class="cn.yf.pojo.Car">
    <!-- 通过constructor-arg的name属性，指定构造器参数的名称，为参数赋值 -->
    <constructor-arg name="speed" value="100" />
    <constructor-arg name="price" value="99999.9"/>
</bean>
```

### 工厂注入

静态工厂和实例工厂。。。。
待补充。。。。

### 自动注入

使用注解的方式为Bean注入属性值
- 如果Bean依赖于其他Bean（比如User依赖Car），那么我们可以使用`@Autowired`或者`@Resource`这两个注解进行依赖注入
- 如果要为基本数据类型或者是Java的封装类型（比如String）赋值，可以使用`@Value`注解。


#### @Autowired和@Resourse 
1. 位置不同  
`@Resourse`是`javax.annother`包提供的一个注解,是`Java EE`的方法，属于标准注解，但Spring也支持该注解的导入；  而`@Autowired`是`Spring`提供的关键字。  

2. 注入方式不同
`@Autowired`是`byType`的，即根据类型注入如果存在多个同样类型的`Bean`，就会报出`BeanCreationException`的错误，要解决这个问题,使用`@Qualifier`注解，`@Qualifier("classname")`来让Spring根据Bean的名称来进行装配。


```java
//通常是实现一个接口的两个类
//第一个
@Service
public class UserServiceImpl1 implements UserService {
   
}
//第二个
@Service
public class UserServiceImpl2 implements UserService {
   
}

//此时@Autowired按类型注入就不知道该注入哪个类了，因为UserServiceImpl1和UserServiceImpl2都是UserService接口的实现类，所以需要用@Qualifier指定一下类名
@RestController
public class UserController {

    @Autowired
    @Qualifier("UserServiceImpl2")
    UserService userService;
}

```

而`@Resourse`关键字是`byName`的，即根据名称注入,如果没有在使用`@Resource`时指定Bean的名字，同时Spring容器中又没有该名字的Bean,这时`@Resource`就会退化为`@Autowired`即按照类型注入，这样就有可能违背了使用`@Resource`的初衷。所以建议在使用`@Resource`时都显示指定一下`Bean`的名字`@Resource(name="xxx")`


## 其他常用注解

下面再继续介绍一些常用注解

注解|说明
-|:-
`@Configration`|Java Config配置文件
`@Import`|手动将类导入Spring容器，以生成Bean
`@Conditional`|条件注解，通常作用于方法，方法返回true则生成组件，返回false则不生成


1. 首先定义一些Service
```java
package com.example.beantest.service;
//注意：这里没有使用@Service注解
public class Service1 {
}

//----------
package com.example.beantest.service;
//注意：这里没有使用@Service注解
public class Service2 {
}

package com.example.beantest.service;
//注意：这里没有使用@Service注解
public class Service3 {
}

```
2. 定义配置文件  

```java
@Configuration  //定义Java Config
@Import({Service1.class, Service2.class})
//此处使用@Import注解，将Service1和Service2两个类导入Spring容器（即ApplicationContext），生成Bean
public class BeanConfig {

    //配置一些SpringBean
    @Conditional(MyBeanCondition.class)  
    //MyBeanCondition是条件配置类(见下文3.)，需要继承Condition接口，
    //重写接口的matches()方法，调用返回true则生成Bean，false则不生成
    @Bean
    public Service3 getService3(){
        return new Service3();
    }
}
```

3. 配置文件MyBeanCondition

```java
//条件配置，继承Condition接口，重写matches方法
public class MyBeanCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(new Random().nextInt(10) >= 5){
            return true;
        }
        return false;
    }
}
```

4. 测试结果

```java
@SpringBootApplication
public class BeanTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BeanTestApplication.class, args);

        Service1 service1Bean = context.getBean(Service1.class);
        System.out.println(service1Bean);

        Service2 service2Bean = context.getBean(Service2.class);
        System.out.println(service2Bean);

        Service3 service3Bean = context.getBean(Service3.class);
        System.out.println(service3Bean);
    }
}
```
结果
```
...
2021-08-01 16:29:30.660  INFO 7912 --- [           main] c.example.beantest.BeanTestApplication   : Started BeanTestApplication in 1.566 seconds (JVM running for 2.566)
com.example.beantest.service.Service1@5b6e8f77
com.example.beantest.service.Service2@41a6d121
Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.beantest.service.Service3' available
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:351)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:342)
	at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1172)
	at com.example.beantest.BeanTestApplication.main(BeanTestApplication.java:22)
```


---

这部分主要是Spring IoC注解的使用，