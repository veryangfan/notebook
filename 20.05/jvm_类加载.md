# 类加载
# 1 什么时候加载
类加载是惰性的，如果我们没用用到类，这个类就不会加载。如果类中有静态变量则会直接加载，如果new对象也会开始加载。
# 2 谁加载
类加载器。

BootstrapLoader，启动类加载器，加载rt包下的一些jdk基础的类，在启动的时候需要的很多类都是这里面的，他们由BootstrapLoader加载。

ExtClassLoader，扩展(Extension)类加载器，加载一些扩展类，例如ext包下的一些类是他加载的。

AppClassLoader，应用程序类加载器，加载classpath下各种用户类，入口main函数所在的类就是用户类，是由AppClassLoader加载。

三者为父子关系，但是Boot是Ext的父，Ext是App的父。原则上当前类中加载的对象，会优先用当前类的类加载器尝试加载。例如
```java
class A{
    void test(){
        B b = new B();
        String c = new String();
    }
}
```
上面的用户类B的加载器是跟A类的加载器一样的，而rt类String的类加载器会先尝试A的类加载器，根据双亲委派最终用BootStrapLoader加载好的String类。
## 2.1 双亲委派
通俗讲，就是要加载类的时候找到了类加载器，但是不是立即用这个加载器加载。而是先判断当前类加载器的父类加载器是否加载过这个类，如果已经加载过了则直接返回父类加载器加载好的类对象，否则才用当前类加载器加载。

例如上面的B，先找到AppCL，找父Ext，未加载，找父Boot，未加载，最终决定由AppCL来加载。再比如上面的String，先找AppCL，找父Ext，未加载，找父Boot，String是rt中的类已经由Boot加载。所以直接返回的这个AppCL加载过的String类对象。即这里c这个对象的对象头中Klass指针，指向的Klass是Boot加载好的String对应的Klass。
# 3 怎么加载
加载class文件字节码->连接(验证、准备、解析)->初始化->[使用->卸载]。
![](https://note.obs.cn-north-4.myhuaweicloud.com/%E7%B1%BB%E5%8A%A0%E8%BD%BD%E8%BF%87%E7%A8%8B.png)

- 加载: 查找并加载类的二进制数据，在内存中生成一个`java.lang.Class`对象
- 连接：连接包括三部分内容，即验证、准备、解析。1）验证，主要是对文件格式、字节码、元数据、符号引用进行验证。2）准备，为类的静态变量分配内存，并设定好初始值。3）解析，将类中的符号引用替换为直接引用的过程。(具体见5补充tip)
- 初始化：为类的静态变量赋初值
- 使用：`new`出对象在程序中使用
- 卸载：垃圾回收  


java1.8主要涉及Metaspace部分，一个ClassLoader对应一部分Metaspace中的内存，这部分来放置类的元数据，例如Klass，itable，vtable等。当新的类加载的时候Metaspace就会变大，而当这个类加载器加载的所有对象和Class对象都被gc掉之后，也会将Metaspace清理，这个过程也是类卸载的过程。

# 4 应用场景
利用自定义类加载器解决冲突问题，之前讲过，参考这个[项目](https://github.com/sunwu51/ClassloaderDemo)，这也是阿里的Pandora容器干的事情。

# 5 补充tip
- 符号引用：符号引用以一组符号来描述所引用的目标，符号可以是任何形式的字面量，只要使用时能够无歧义的定位到目标即可。在编译时，java类并不知道所引用的类的实际地址，因此只能使用符号引用来代替。例如，在Class文件中它以CONSTANT_Class_info、CONSTANT_Fieldref_info、CONSTANT_Methodref_info等类型的常量出现。

- 直接引用： 直接引用可以是  
（1）直接指向目标的指针（比如，指向“类型”`Class`对象、类变量、类方法的直接引用可能是指向方法区的指针）。  
（2）相对偏移量（比如，指向实例变量、实例方法的直接引用都是偏移量）。  
（3）一个能间接定位到目标的句柄。

直接引用是和虚拟机的布局相关的，同一个符号引用在不同的虚拟机实例上翻译出来的直接引用一般不会相同。如果有了直接引用，那引用的目标必定已经被加载入内存中了。