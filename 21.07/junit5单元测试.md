#Junit5 单元测试

- [常用注解](#%E5%B8%B8%E7%94%A8%E6%B3%A8%E8%A7%A3)
    - [@Test 和Assertions](#test-%E5%92%8Cassertions)
    - [@BeforeEach, @BeforeAll, @AfterEach, @AfterAll](#beforeeach%C2%A0beforeall-aftereach-afterall)
    - [@SpringBootTest 搭配 @Autowired使用](#springboottest-%E6%90%AD%E9%85%8D-autowired%E4%BD%BF%E7%94%A8)
    - [@MockBean，@SpyBean搭配规则条件配置，如静态方法when](#mockbeanspybean%E6%90%AD%E9%85%8D-%E8%A7%84%E5%88%99%E6%9D%A1%E4%BB%B6%E9%85%8D%E7%BD%AE%E5%A6%82%E9%9D%99%E6%80%81%E6%96%B9%E6%B3%95when)
- [写法规范](#%E5%86%99%E6%B3%95%E8%A7%84%E8%8C%83)


## 常用注解
### 1. `@Test` 和`Assertions`

添加了@Test注解后，可以使测试方法直接运行
Assertions类调用断言相关的静态方法
```java
public class Test1 {
    @Test
    void fun1(){
        int res = 1 + 1;
        //断言Junit4.x是 Assert, 5.x版本更新为 Assertions
        Assertions.assertEquals(3,res);  //判断是否相等
        Assertions.assertTrue(3 == res);  //用布尔值的方式判断
    }
}
```
### 2.  `@BeforeEach`, `@BeforeAll`, `@AfterEach`, `@AfterAll`

- BeforeEach / @AfterEach  在每一个测试函数之前（后）执行一些操作，每一次都执行
- @BeforeAll / @AfterAll  在所有测试函数之前（后）执行一些操作，且只执行一次
```java
public class Test2 {
    @BeforeAll
    static void BeforeAll(){
        System.out.println("init once");
    }

    @AfterAll
    static void AfterAll(){
        System.out.println("after once");
    }

    @BeforeEach
    void BeforeEach(){
        System.out.println("init");
    }

    @AfterEach
    void AfterEach(){
        System.out.println("after");
    }

    @Test
    public void t1(){
        System.out.println(123);
    }

    @Test
    public void t2(){
        System.out.println(456);
    }
}
```
运行结果
```
init once
init
123
after
init
456
after
after once
```
### 3. `@SpringBootTest` 搭配 `@Autowired`使用
添加@SpringBootTest注解与@Autowired搭配使用，可以使Service实现自动注入
```java
@SpringBootTest
public class Test3 {

//    @Test
//    void t1(){
//        int res = new Svc1().add(1,1);  //此时需要new Service
//        Assertions.assertEquals(2,res);
//    }

    @Autowired
    Svc1 svc1;  //Spring自动注入

    @Test
    void t1(){
        int res = svc1.add(1,1);
        Assertions.assertEquals(2,res); 
    }
}
```

### 4. `@MockBean`，`@SpyBean`搭配规则条件配置，如静态方法`when()`
`@MockBean` 模拟操作，通常应用于涉及到对数据库等操作的测试
`@SpyBean` 根据配置规则执行，配置了规则就按照规则执行，否则不执行（此时等同于`@MockBean`）规则配置可以使用`when()`等静态方法（`Mockito`）

- `@MockBean`  demo
```java
@SpringBootTest
public class Test3 {
    @MockBean
    Svc1 svc1;

    @Test
    void t1(){
        //Mock配置规则只指定了加法;
        int res = svc1.add(1,1);  //mock后res = 0
        Assertions.assertEquals(2,res); 
    }
}
```
运行结果
```
org.opentest4j.AssertionFailedError: 
Expected :2
Actual   :0
```
- `@SpyBean`  demo
```java
import static org.mockito.Mockito.when;  //when方法

@SpringBootTest
public class Test3 {

    @SpyBean  //配置了规则就按照规则执行，没有配置就不执行
    Svc1 svc1;

    @Test
    void t1(){
//        Mock配置规则指定了1+1=3
        when(svc1.add(1,1)).thenReturn(3);
        int res = svc1.add(1,1);
        Assertions.assertEquals(2,res); //
    }
}
```
运行结果
```
org.opentest4j.AssertionFailedError: 
Expected :2
Actual   :3
```

## 写法规范
1. 通常一个Service对应一个ServiceTest  
2. Service里的方法可能对应一个或多个方法，因为涉及正常输入、异常输入、特例输入、边界值等
