# Spring面试题目
## 什么是IOC？ 
IOC就是控制反转，可以使应用程序在运行时依赖 IoC容器 来动态注入对象需要的外部资源。  
最直观的表达就是，IOC让对象的创建不用去new了，可以由spring自动注入生产，根据配置文件在运行时动态的去创建对象以及管理对象。

作用：解耦合，IoC让相互协作的组件保持松散的耦合。

## 什么是AOP，应用场景？
AOP，一般称为面向切面，作为面向对象的一种补充，用于将那些与业务无关，但却对多个对象产生影响的公共行为和逻辑，抽取并封装为一个可重用的模块。可用于权限认证、打印日志等

## Spring的Bean的生命周期？ 
Bean 的生命周期概括起来就是 4 个阶段：

实例化（Instantiation）
属性赋值（Populate）
初始化（Initialization）
销毁（Destruction）

## Spring的Bean是如何注入的？


## Spring的Bean的类型，除了单例还可以配置什么？ 

1. singleton 单例，是默认值。

2. prototype 原型Bean，每执行一次getBean将获得一个实例。例如：struts整合spring，配置action多例。

3. request：每次http请求将会有各自的bean实例，类似于prototype。

下面这两种不太懂
4. session：在一个http session中，一个bean定义对应一个bean实例。
5. global session：在一个全局的http session中，一个bean定义对应一个bean实例。典型情况下，仅在使用portlet context的时候有效。

## @Autowired和@Resourse 

1. `@Resourse`是javax.annother包提供的一个注解关键字,是`Java EE`的方法，但Spring也支持该注解的导入；
  而`@Autowired`是`Spring`提供的关键字。
2. @Autowired是byType的;
  @Resourse关键字是byName的,如果存在多个同样类型的Bean，就会报出BeanCreationException的错误，要解决这个问题,使用@Qualifier关键字，@Qualifier(***)来让Spring根据Bean的名称来进行装配。

## SpringMVC的执行流程？

1. Spring MVC 将所有的请求都提交给 `DispatcherServlet`，它会委托应用系统的其他模块负责对请求进行真正的处理工作  
2. `DispatcherServlet` 查询一个或多个 `HandlerMapping`，找到处理请求的 `Controller`  
3. `DispatcherServlet` 请求提交到目标 `Controller`  
4. `Controller` 进行业务逻辑处理后，会返回一个 `ModelAndView`  
5. `Dispatcher` 查询一个或多个 `ViewResolver` 视图解析器,找到 `ModelAndView` 对象指定的视图对象  
6. 视图对象负责渲染返回给客户端  