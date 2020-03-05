# Java基础部分
---
## 一、 安装环境
JVM ：英文名称（Java Virtual Machine），就是我们耳熟能详的 Java 虚拟机。它只认识 xxx.class 这种类型的文件，它能够将 class 文件中的字节码指令进行识别并调用操作系统向上的 API 完成动作。所以说，jvm 是 Java 能够跨平台的核心，具体的下文会详细说明。

JRE ：英文名称（Java Runtime Environment），Java运行时环境。它主要包含两个部分，jvm 的标准实现和Java的一些基本类库。它相对于jvm来说，多出来的是一部分的 Java 类库。

JDK ：英文名称（Java Development Kit），Java开发工具包。jdk是整个Java开发的核心，它集成了 jre 和一些好用的小工具。例如：javac.exe，java.exe，jar.exe 等。

显然，这三者的关系是：一层层的嵌套关系。JDK>JRE>JVM。

>- 【问题】Java为什么能跨平台，实现一次编写，多处运行？  
>Java 能够跨平台运行的核心在于 JVM 。不是 Java 能够跨平台，而是它的 jvm 能够跨平台。我们知道，不同的操作系统向上的 API 肯定是不同的，那么如果我们想要写一段代码调用系统的声音设备，就需要针对不同系统的 API 写出不同的代码来完成动作。
>而 Java 引入了字节码的概念，jvm 只能认识字节码，并将它们解释到系统的 API 调用。针对不同的系统有不同的 jvm 实现，有 Linux 版本的 jvm 实现，也有 Windows 版本的 jvm 实现，但是同一段代码在编译后的字节码是一样的。引用上面的例子，在 Java API 层面，我们调用系统声音设备的代码是唯一的，和系统无关，编译生成的字节码也是唯一的。但是同一段字节码，在不同的 jvm 实现上会映射到不同系统的 API 调用，从而实现代码的不加修改即可跨平台运行。

[参考原文](https://blog.csdn.net/qq_35326718/article/details/79443911)

---

## 二、 基本程序设计
#### 1. 学习Java，从【Hello,World！】开始
```java
public class FirstSample{
    public static void main(String[] args){
        System.out.println("Hello,World!");l
    }
}
```
- (1) Java区分大小写  
- (2) FirstSample 类名使用驼峰命名法(CamelCase)  
- (3) 根据Java命名规范，main()方法必须声明为public.(JaveSE 1.4及更高版本)  
- (4) 使用了System.out对象并调用了println方法，点号(.)用于调用方法

#### 2. 注释
- (1)单行注释：使用 //
- (2)多行注释：使用/*和 */将需要注释的内容括起来
- (3)文档注释：以/**开始，以 */结束，这种注释可以自动生成文档

#### 3. 数据类型
- Java是一种强类型语言。在Java中，一共有8种基本类型
- 其中，4种整型（byte,short,int,long）,2种浮点型（float,double）,一种字符类型char(使用Unicode编码)，一种布尔类型(boolean)
- 所有基本类型都有对应的包装类型：Byte、Short、Integer、Long、Float、Double、Character、Boolean，其重要方法可以查阅API


##### (1) 整型
- 整型用于表示没有小数部分的数值，允许是负数。

类型|大小|范围|备注
:-:|:-:|:-:|:-:
byte|1字节|-128~127|( 2^7 ~ 2^7-1 )
short|2字节|-32768~32767|( 2^15 ~ 2^15-1 )
int|4字节|-2147483648~2147483647|( 2^15 ~ 2^15-1)刚好超过20亿
long|8字节|-9223372036854775808~9223372036854775807|(2^31 ~ 2^31-1)

>【注】
>- long数值有一个后缀L或l
>- 把一个较大的值赋给int变量，系统是不会自动转成long的，除非再后面加L
>- Java没有任何形式的无符号类型(undigned)

##### (2) 浮点类型
- 浮点类型用于表示有小数部分的数值。

类型|大小|范围|备注
:-:|:-:|:-:|:-:
float|4字节|大约±3.40282347E+38F|(有效位数为6~7位)
short|8字节|大约±1.79769313486231570E+38F|(有效位数15位)

>【注】
>- float数值有一个后缀F或f
>- 下面是用于表示溢出和出错的三个特殊浮点数值
>   - 正无穷大 ——> Double.POSITIVE_INFINITY(=1.0/0.0)
>   - 负无穷大 ——> Double.NEGATIVE_INFINITY(-1.0/0.0)
>   - NaN(非数字) ——> Double.NaN(0.0d/0.0)
>- 浮点数值不适合用于无法接受舍入误差的金融计算中。浮点数采用二进制数表示（二进制无法精确表示1/10，就像十进制无法精确表示1/3），System.out.println(2.0-1.1)将打印出0.89999999999999，而不是0.9。应该使用BigDecimal类。

```java
//由于精度原因，不能用等号判断两个小数是否相等

double double d1,d2;
 ···
   if(d1==d2)
 ···
```

##### (3) char类型
- Java语言使用16位的Unicode字符集作为编码方式
- **char是可以表示一个中文字符的**，不管中英文统一占两个字节

> 关于字符和字节的问题
>- ASCII码:一个ASKII码是一个字节
>- UTF-8编码:一个英文字符等于一个字节，一个中文字符等于三个字节
>- Unicode编码：一个英文等于两个字节，一个中文（含繁体）等于两个字节

char类型的变量经过 `+ 、-`等操作后，得到的值为int类型。所以，将char类型的变量转化为int类型，可以使用`c-'0'`的方法。 
```java
    char c1 = '1';
    char c2 = '2';
    //char c3 = c1+c2;
    int i1 = c1 + c2;
    int i2 = c1 - '0';
```

##### (4) boolean类型
- boolean类型有两个值，false和true,用来判定逻辑条件。整型和布尔型不能相互转换。

##### (5) 类型间的相互转换

- 自动类型转换：当把一个表数范围小的数值或变量直接赋给另一个表数范围大的变量时，系统可以进行自动类型转换(不会丢失数据)。
>Java支持自动类型转换的类型:  
>- byte——>short——>int——>long——>float——>double
>- char——>int——>long——>float——>double
- 强制类型转换：如果需要把箭头右边的类型转换成左边的类型，则需要强制转换，强制转换可能引起信息丢失。
- 表达式类型的自动提升：一个算数表达式中包含多个基本类型的值时，整个算数表达式的数据类型将发生自动提升。

>类型提升规则：
> 1. 所有的byte,short,char都将被提升到int 
> 2. 整个算数表达式的数据类型，自动提升到与表达式中最高等级操作数同样的类型。
```java
//运行下面的程序，正确的结果是：
class func{
    public static void main(String[] args){
        byte a = 1;
        System.out.println(a += 1); // a+=1等价于a=(int)(a+1),+=运算符自动转换，但a=a+1就会报错了
        byte f = a + 1;
        System.out.println(f);
    }
}

答案：2，错误
```

#### 4.字符串
##### (1) 不可变字符串
- String类没有提供用于修改字符串的方法，如果希望将greeting的内容改为Help，不能直接将最后面两个字母修改为'p'和'!'。应该首先提取需要的字符，在拼接上替换的字符串。

```java
String greeting = "Hello";
greeting = greeting.substring(0,3) + "p!";
greeting = "Help!"
```

由于不能修改Java字符串中的字符，所以在Java文档中将String类对象称为**不可变字符串**。你可能会问，上面的代码不是将greeting由"Hello"修改为"Help!"了吗？不是的，**greeting只是一个引用，所以说，这里的字符串并不是可变，只是变更了字符串引用。**

##### (2)检测字符串是否相等

可以使用equals方法检测两个字符串是否相等。  
```java
s.equals(t);
"Hello".equals(greeting); //推荐，常量放在前面可以避免空指针异常
```
一定不要用`==`检测两个字符串是否相等。`==`只能确定两个字符串是否放在同一个位置上（即判断地址是否相同）。JVM只会将字符串常量共享，而"new"/"substring"/"+"等操作产生的结果并不是共享的。
```java
public class Main {
    public static void main(String[] args) {
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1 == s2); //true
        String s3 = new String("abc");
        System.out.println(s1 == s3); //false
    }
}
```
- 空串与null串
空串`""`是一个长度为0的字符串，是一个Java对象，有自己的长度(0)和内容(空)。
```java
//空串判断方式
if(str.length()==0)
//或者
if(str.equals(""))
```
String类型变量还可以存放一个特殊的值null。
```java
if(str == null) //检查str是否为null
if(str != null && str.length() != 0) //既不是null也不是空串
```

- **构建字符串(StringBuilder)**

有时候需要由较短的字符串构建字符串。采用字符串拼接的方式达到此目的效率较低，每次拼接字符串，都会构建一个新的string对象，造成了对时间和空间的浪费。使用StringBuilder可以避免这个问题。

```java
StringBuilder builder = new StringBuilder;
builder.append(ch);
builder.append(str);
String completedString = builder.toString();
```
>StringBuilder这个类的前身是StringBuffer,其效率有些低，但允许采用多线程的方式进行添加和删除字符的操作（**StringBuffer是线程安全的**）。单线程的情况下则应该使用StringBuilder.这两个类的API是相同的。

#### 5.输入输出
- 标准输出流：System.out.println
- 标准输入流：System.in
```java
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            //注意while处理多个case              
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }
    }
}
```
>**常用API**
- String nextLine()   读取下一行内容
- String next() 读取下一个单词（以空格作为分隔符）
- int nextInt()
- double nextDouble()
- boolean hasNext() 检测是否还有其他单词
- boolean hasNextInt() 检测是否还有其他单词
- boolean hasNextDouble() 检测是否还有其他单词

---

## 三、面向对象
面向对象的三大特征：封装、继承、多态。
#### 1. 类与对象

类Class是对象的特征抽象，对象是类的具体实现。
#### 2. 封装

- 访问修饰符:`public, private, defult, protected`
这个没有必要死记硬背，我们来理一下：  
public —— 所有地方都以可以访问
private —— 只有本类可以访问
这两种是比较常用的，此外
defult（什么都不写）—— 本包(pakage)可以访问
protected —— 除了本包外，还有其他包的子类也可以访问

- 封装
把没有必要暴露的操作逻辑藏起来 ———— 用`private`修饰
这样做有什么好处呢？一是代码逻辑简单（只看API就行了，怎么实现的可以不看，不影响使用）; 二是安全，调用时不修改代码逻辑，传参调用拿返回值就可以了（或者直接看效果，无参无返回）。


#### 3. 继承

子类继承父类，Java中使用extends关键字。
- 覆盖方法
子类继承父类会继承其全部暴露的方法，但有些方法子类继承下来未必有用，可能需要修改，此时就需要覆盖方法。子类可以增加和修改（覆盖）继承的属性和方法，但无法删除。
子类中调用父类的方法，可以使用super关键字。
```java
class SuperClass{
    private int a = 5;
    public int getA(){
        return a;
    }
}

public class SubClass1 extends SuperClass{
    //继承的类不能直接访问父类的私有域，只能调用公有的get方法
    public int getA(){
        return a+1; //won't work
    }

    //必须加上spuer.getA(),否则会自己调用自己，直到程序崩溃
    public int getA(){
        return getA()+1; //still won't work
    }
}

public class SubClass2 extends SuperClass{
    private int a = 7;
    public int getA(){
        return a+1;
    }
    public int getSuperAAddOne(){
        return super.getA()+1;
    }

    public static void main(String[] args) {
        SubClass2 subClass = new SubClass();
        System.out.println(subClass.getA()); //8
        System.out.println(subClass.getSuperAAddOne());//6
    }
}
```

> 覆盖和重载没什么关系。重载是在一个类里，根据参数的个数及类型的不同，对方法进行重载。两者没有任何联系。

#### 4. 多态

- 规则
有一个判断是否需要使用继承关系的规则，"is-a"规则，也叫做"置换法则",它表明程序中出现父类对象的任何地方都可以用子类对象置换。

在Java中，对象变量是**多态**的，一个SuperClass变量既可以引用一个SuperClass对象，也可以引用任何一个子类的对象（如SubClass1，SubClass2等）。

```java
//父类引用可以指向子类对象
SuperClass superClass1 = new SuperClass();
SuperClass subClass1 = new SubClass();

//然而，子类引用不能指向父类对象
SubClass subClass2 = superClass1;//error

//子类数组的引用可以直接转换成父类数组的引用，不需要强制转换
SubClass subClass3 = new SuperClass();
SuperClass superClass2[] = subClass3;

```
上面代码中，subClass1可以调用可以调用父类的方法，但反过来不行。

- 理解方法的调用过程

   - **方法签名**：方法的名字和参数列表称为方法的签名。例如，f(int)和f(String)是两个具有相同名字不同签名的方法。如果在子类中定义了一个与父类签名相同的方法，那么子类中的这个方法就覆盖了父类中这个签名相同的方法。
   特别说明的是，返回值不是方法签名的一部分。

**调用过程：**

第一步：编译器查看对象的声明类型和方法名，假设要调用x.f(param),且隐式参数x声明为C类的对象。需要注意的是，有可能存在多个名字为f，但参数类型不一样的方法。例如，可能会存在方法f(int)和f(String)。编译器将会一一列举出C类中名为f的方法 和其父类中访问属性为public且名为f的方法（父类的私有方法不可以访问）。
至此，编译器已经获得所有可能被调用的候选方法。

第二步：编译器将查看调用方法时提供的参数类型。如果在所有名为f的方法中存在一个与提供的类型完全匹配，就选这个方法，这个过程叫做**重载解析**。例如，对于x.f("Hello")来说，编译器会挑选f(String),而不是f(int).由于允许类型转换(int 可以转成double),所一这个过程可能很复杂，如果编译器没有找到与参数类型匹配的方法，或者经过类型转换后有多个类型与之匹配，就会报错。
至此，编译器已获得需要调用的方法名字和参数类型。

>[注释]：
>静态绑定：如果是private方法、static方法、final方法、构造方法，那么编译器可以准确的知道该调用哪个方法，这种调用成为静态绑定。
>动态绑定：调用的方法依赖于隐式参数（隐式参数就是调用对象）的实际类型，在运行时实现动态绑定。

当程序运行，并且采用动态绑定调用方法时，虚拟机通过搜索，调用与x所引用的对象实际类型最合适的那个类的方法。先在自己的类中找，如果找不到，再去其父类中寻找。
每次调用方法都搜索，时间开销太大。因此，虚拟机预先为 每个类 创建了一个方法表（method table）,其中列出了所有方法的签名和实际调用的方法。

动态绑定有一个非常重要的特性：无需对现存的代码进行更改，就可以对程序进行拓展。

#### 5. final关键字：阻止继承

final关键字的几种用法：
- (1) 修饰类：表示此类不可被继承。
    - a. 被final修饰的类，final类中的成员变量可以根据自己的实际需要设计为final。
    - b. final类中的方法都会被隐式的指定为final方法。
- (2) 修饰基本数据类型：表示该变量只能赋值一次，无法修改；必须赋初始值。
- (3) 修饰引用类型：则在对其初始化之后便不能再让其指向其他对象了，但该引用所指向的对象的内容是可以发生变化的。
    - a. 本质是一样的，因为引用的值是一个地址，final要求值-即地址的值不发生变化。
- (4) 修饰方法：表示该方法不能被覆盖
    - a. 一个类的private方法会隐式的被指定为final方法。
    - b. 如果父类中有final修饰的方法，那么子类不能去重写。
- (5) 修饰形参：表示该变量在生存期中，值不能被改变。

#### 6. 抽象类

- 抽象方法使用abstract关键字进行修饰，不需要实现（没有大括号）。
- 为了提高程序的清晰度，规定含有一个或多个抽象方法的类必须被声明为抽象的，抽象类使用abstract关键字声明。抽象类中也可以含有带具体实现的方法。
- 类即使不含有抽象方法，也可以被声明为抽象的。
- 抽象类不能被实例化。但可以定义一个抽象类的变量，引用非抽象子类的对象。

#### 7. Object类:所有类的父类
Object类中方法概览：
```java
public final native Class<?> getClass()

public native int hashCode()

public boolean equals(Object obj)

protected native Object clone() throws CloneNotSupportedException

public String toString()

public final native void notify()

public final native void notifyAll()

public final native void wait(long timeout) throws InterruptedException

public final void wait(long timeout, int nanos) throws InterruptedException

public final void wait() throws InterruptedException

protected void finalize() throws Throwable {}
```

Java中所有类的始祖，在Java中每个类都是由它拓展而来的。

- equals方法
Object类中的equals方法用于检测一个对象是否等于另外一个对象。在Object类中，这个方法将判断两个对象是否具有相同的引用。

- hashCode方法
散列码 是由对象导出的一个整型值，散列码是没有规律的，如果x和y是两个不同的对象，那么x.hashCode和y.hashCode基本不会相同。由于hashCode方法定义在Object类中，因此每个对象都有一个默认的散列码，其值为对象的存储地址。

```java
//String类使用下列算法计算散列码
int hash = 0;
for(int i=0;i<length;i++)
    hash = 31*hash + charAt(i);
```
- toString方法
用于返回表示对象值的字符串。

```java
class StringTest{
    public static void main(String[] args) {
        int[] num = {1,2,3,4,5}; 
        System.out.println(num); // [I@1b6d3586 前缀I表示是整型数组
        System.out.println(Arrays.toString(num));// [1, 2, 3, 4, 5] 
    }
}
```
#### 8. 参数数量可变的方法
可以通过`...`定义变参方法。

```java
public static double max(double... values){
        double maxValue = Double.NEGATIVE_INFINITY;
        for(double v : values)
            if(v>maxValue)
                maxValue = v;
        return maxValue;
    }
```

#### 9. 枚举类 Enum

Java5中新增了一个enum关键字，用以定义枚举类。
```java
//SeasonEnum.java
public enum SeasonEnum {
    SPRING("春天"),SUMMER("夏天"),FALL("秋天"),WINTER("冬天");
    private String desc;
    private SeasonEnum(String desc){
        this.desc = desc;
    }
    public String getDesc(){
        return desc;
    }
}

//EnumTest.java
public class EnumTest {
    public static void main(String[] args) {
        //所有的枚举类都有一个values()方法，返回该枚举类值的数组
        //SeasonEnum[] se = SeasonEnum.values();
        for(SeasonEnum s:SeasonEnum.values()){
            System.out.println(s);
        }
        SeasonEnum se = Enum.valueOf(SeasonEnum.class, "SPRING"); //
        System.out.println(se.getDesc()); //春天
        System.out.println(se.ordinal()); //0
    }
}
```
所有的枚举类型都是Enum类的子类，他们继承了Enum类的许多方法
>【API】java.lang.Enum<E>
- static Enum valueOf(Class EnumClass,String name);
返回指定名字，给定类的枚举常量
- String toString()
返回枚举常量名
- int ordinal()
返回枚举常量的位置索引，从0开始
- int compareTo(E other)
如果枚举常量出现在other之前，返回负值；之后，返回正值；如果this==other,返回0






#### 10. 反射

#### 11. 接口
接口不是类，而是对类的一组描述，这些类要遵从接口描述的统一格式进行定义。一个类可以实现(implement)一个或多个接口。
下面是Comparable接口的代码,这段代码表明。任何实现Comparale接口的类都要包含compareTo方法，并且这个方法的参数必须是一个Object对象，返回一个整型数值。
```java
public interface Comparable<T>{
    //接口中的方法自动地属于public,因此不必提供任何关键字
    int compareTo(Object other); 
}
```
 ##### 接口的特性：
- 接口不是类，不能实例化，但接口可以声明引用类型变量
- 接口变量必须引用实现了该接口的类的对象
- 接口可以继承接口
- 实现接口与类的继承相似，一样可以获得实现的接口的常量、抽象方法、内部类和枚举类

 ##### 接口的使用：
 - 自定义集合排序方式：Comparator接口——重写compare()方法
下面的代码是对于JsonArray按照某个属性进行排序：
```java
/**
 *  工具函数： 对JSONArray，按照allCount从大到小进行排序
 * @param jsonArray
 * @return
 */
public JSONArray sortJSONArray(JSONArray jsonArray){
    List<JSONObject> list = JSONArray.parseArray(jsonArray.toJSONString(), JSONObject.class);
    //普通匿名内部类法,它的含义是，创建一个实现Comparator接口的类的新对象
    Collections.sort(list, new Comparator<JSONObject>() {
        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            int a = o1.getInteger("allCount");
            int b = o2.getInteger("allCount");
            if (a > b) {
                return -1;
            } else if(a == b) {
                return 0;
            } else
                return 1;
            }
    });

    //lambda表达式写法
    Collections.sort(list, (JSONObject o1, JSONObject o2)->{
            int a = o1.getInteger("allCount");
            int b = o2.getInteger("allCount");
            if (a > b)
                return -1;
            else if(a == b)
                return 0;
            else
                return 1;
    });

    JSONArray jsonSort = JSONArray.parseArray(list.toString());
    return jsonSort;
}
```

 - 对象克隆：Cloneable接口——重写clone()方法

#### 12. lambda表达式  
lambda表达式是一个可传递的代码块，可以在以后执行一次或多次。
- lambda表达式语法
lambda表达式形式：参数，箭头(->)，以及一个表达式。如果要完成的计算无法放在一个表达式中完成，把这些代码放在{}中，并包含隐式的return语句。无需指定lambda表达式的返回值类型，lambda表达式返回类型总是会由上下文推导得出。
```java
(String first,String second)->first.length-second.length

(String first,String second)->{
    if(first.length < second.length) return -1;
    else if(first.length > second.length) return 1;
    else return 0;
}

//无参也要保留括号
()->{for(int i=0;i<10;i++) System.out.println("i");}
```
- 函数式接口
除了在语言层面支持函数式编程风格，Java 8也添加了一个包，叫做 java.util.function。它包含了很多类，用来支持Java的函数式编程。其中一个便是Predicate，使用 java.util.function.Predicate 函数式接口以及lambda表达式，可以向API方法添加逻辑，用更少的代码支持更多的动态行为。下面是Java 8 Predicate 的例子，展示了过滤集合数据的多种常用方法。Predicate接口非常适用于做过滤。
```java
package package6;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LambdaTest {
    public static void main(String[] args){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        System.out.println("Languages which starts with J :");
        filter(languages, (str)->str.startsWith("J")); //Java

        System.out.println("Languages which ends with a ");
        filter(languages, (str)->str.endsWith("a")); //Java Scala

        System.out.println("Print all languages :");
        filter(languages, (str)->true); //Java Scala C++ Haskell Lisp

        System.out.println("Print no language : ");
        filter(languages, (str)->false);

        System.out.println("Print language whose length greater than 4:");
        filter(languages, (str)->str.length() > 4); //Scala Haskell
    }

    public static void filter(List<String> names, Predicate<String> condition) {
        for(String name: names)  {
            if(condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }
}
  
```
在java 8中已经为我们定义了很多常用的函数式接口它们都放在java.util.function包下面,一般有以下常用的四大核心接口：
函数式接口|类型|参数类型|返回类型|抽象方法名|描述
:-:|:-:|:-:|:-:|:-:|:-:
Consumer<T>|消费型接口|T|void|void accept(T t)|对类型为T的对象应用操作
Supplier<T>|供给型接口|无|T|T get()|返回类型为T的对象
Function<T, R>|函数型接口|T|R|R apply(T t)|对类型为T的对象应用操作并返回R类型的对象
Predicate<T>|断言型接口|T|boolean|boolean test(T t)|确定类型为T的对象是否满足约束


- 方法引用
有时候可能已经有现成的方法，可以完成你想要传递到其他代码的某个动作。
方法引用的格式： 类名::方法名
```java
package package6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LambdaTest2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4,7,9);
        // Lambda表达式
        list.forEach(a -> System.out.println(a));
        System.out.println("********************");
        // 方法引用
        list.forEach(System.out::println);
    }
}

```
表达式`System.out::println`就是一个方法引用(method reference),它等价于lambda表达式`x->System.out.println(x)`

- 变量作用域
在lambda表达式中，只能引用值不会改变的变量。如果在lambda表达式中改变变量，并发执行多个动作时就会不安全。
```java
package package6;

import java.util.Arrays;
import java.util.List;

public class LambdaTest2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(4,7,9);
        int printCount = 0;
        //下面这种写法是错误的
        list.forEach(a -> {
            System.out.println(a);
            //下面这句会产生错误
            printCount++;
        });
        //可以使用for循环
        for(Integer a: list){
            System.out.println(a);
            printCount++;
        }
    }
}

```


#### 13. 内部类
- 非静态内部类
定义：
   - 内部类作为其外部类的成员，可以使用任意修饰符修饰
   - 在外部类里写非静态内部类时，与平时写普通类并没有区别
   - 编译后文件夹生成两个`.class`文件，一个是`Outer.class`,一个是`OuterClass$InnerClass.class`
   - 当非静态内部类访问某个变量时，查找顺序为 方法内->内部类内->外部类内，如果重名，则需要使用this、外部类名.this来区分
   - 非静态内部类成员可以访问外部类的private成员，但反过来就不成立了
   - 非静态内部类的成员只在非静态内部类范围内是可知的。并不能被外部类直接使用。如果外部类需要访问非静态内部类的成员，则必须显式创建非静态内部类对象来调用访问内部类的成员。
   - 不允许在外部类的静态成员在直接使用非静态内部类（静态成员不能访问非静态成员）
- 静态内部类 
定义：用static修饰的内部类
   - 静态内部类是外部类的一个静态成员
   - 静态内部类不能访问外部类的普通成员，只能访问静态成员（静态成员不能访问非静态成员）
   - 外部类的静态方法、静态初始化块中可以使用静态内部类
   外部类依然不能直接访问静态内部类的成员，但可以使用静态内部类的类名调用静态内部类的静态成员；对于非静态成员，依然要显式创建对象来调用。
- 局部内部类
定义：把一个内部类写在方法里，则这个内部类就是一个局部内部类。局部内部类仅在该方法里有效。
- 匿名内部类
最常用的创建匿名内部类的方式是需要创建某个接口类型的对象。
#### 14. 代理

---

## 4. 异常

#### 1. 异常
##### (1) 异常分类
**Java异常被分为两大类，Checked异常和Runtime异常**。所有的RuntimeException类及其子类的实例被称为Runtime异常，其余的异常被称为Checked异常。
在Java中，异常对象都是派生于Throwable类的一个实例。所有的异常都是由Throwable继承而来，但在下一层立即分为了两个分支：Error和Exception
![Java中异常层次结构](https://note.obs.cn-north-4.myhuaweicloud.com/ExceptionClass.png)
Error通常是系统内部错误或者资源耗尽错误，通常会告诉用户错误并安全终止程序；
Exception又分为两个分支，一类派生于RuntimeException,另一个派生于其他异常。
- 派生于RuntimeException的异常包括下面几种情况
   - 错误的类型转换(ClassCastException)
   - 数组访问越界(IndexOutOfBoundsException)
   - 访问null指针(NullPointerException)
- 不是派生于RuntimeException的异常包括下面几种情况（其他异常）
   - 试图在文件末尾读取数据(EOFException)
   - 试图打开不存在的文件(FileNotFoundException)
   - 试图根据给定的字符串查找class对象，而这个字符串表示的类不存在

原则：“如果出现Runtime异常，就是你的问题”。

##### (2) 异常处理
###### try...catch捕获异常
```java
try{
    //TO DO... 
    // 出现异常，生成异常对象ex
}catch(ExceptionClass1 e1){// ex instanceof ExceptionClass1 == true
    //exception handler statement...
}catch(ExceptionClass2 e2){// ex instanceof ExceptionClass2 == true
    //exception handler statement...
}catch(ExceptionClass3|ExceptionClass4 e3){// (ex instanceof ExceptionClass3 == true)||(ex instanceof ExceptionClass4 == true)
    //exception handler statement...
}
... ...
```
如果执行try块代码时出现异常，系统自动生成一个异常对象，该异常对象被提交给Java运行时环境(JRE)，这个过程被称为抛出(throw)异常。
当JRE收到异常对象时，会寻找能处理异常对象的catch块，如果找到了，就交给对应的catch块处理，这个过程称为捕获(catch)异常。
###### 使用finally回收资源
有时候，程序在try块中打开了资源（如数据库连接），这些资源需要被回收。
不管是否有异常被捕获，finally子句中代码始终被执行。
```java
try{
    1
    可能抛异常的语句
    2
}catch(Exception e){
    3
    可能抛异常的语句
    4
}finally{
    5
}
    6
```
- 代码没有抛出异常:这种情况下，先执行try块中全部代码，再执行finally中的语句。
1->2->5->6
- 抛出异常，在catch中被捕获：这种情况下，先执行try块中的代码，直到抛出异常为止，此时跳过try块中剩余代码，转去执行catch中代码。如果catch没有抛出异常，则1->3->4->5->6;如果catch再次抛出异常，则只执行1->3->5
- 抛出异常，没有被catch捕获，则只执行1->5

由此可见，finally语句始终被执行。

>注意：假设利用return语句从try块中退出，在try块中的return执行之前，finally语句将会被执行，如果finally语句中也有返回值，**将覆盖原始的返回值**。
```java
public class FinallyTest {
    public static int f(int n){
        try{
            int r = n*n;
            return n;
        }finally{
            if(n == 2)
                return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(f(2));//0
    }
}

```
###### 访问异常信息
所有的异常对象都包含如下方法：
- String getMessage() 返回该异常详细描述字符串
- void printStackTrace() 将该异常的跟踪栈信息打印出来
- StackTraceElement[] getStackTrace()  返回该异常的跟踪栈信息
//StackTraceElement类中还含有获取文件名和当前执行行号的方法，可以toString

#### 2. 断言


---

## 5. 泛型

为什么要有泛型：类型参数可以使程序具有更好的可读性和安全性
#### 1.泛型类
```java
public class Pair<T>{
    private T first;
    public Pair(T first){ this.first = first;}
    public getFirst(){ return first; }
    public setFirst(T newValue){ first = newValue; }
}

//泛型类可以引入多个变量
public class Pair<T,U>{ ... }
```
#### 2.泛型方法
```java
class ArrqyAlg{
    public static <T> T getMiddle(T... a){
        return a[a.length / 2];
    }
}

//调用
String middle = ArrAlg.<String>getMiddle("John","Q.","Public");
//也可以省略<>,编译器可以自己推断出来
String middle = ArrAlg.getMiddle("John","Q.","Public");
```
有时，需要对泛型变量加以约束
```java
//这里要注意，只是增加条件对变量进行限定，而不是替代变量，和通配符不一样
public static <T extends Comparable> T min(T[] a){ ... }
```

#### 3.泛型代码与虚拟机
虚拟机没有泛型对象，所有对象都属于普通类。
##### (1) 类型擦除
- 原始类型：原始类型的名字就是删去类型参数后的泛型类型名。擦除类型变量，并替换为限定类型（若无限定类型用Object替换）。

##### (2) 不能用基本类型实例化类型参数
没有`Pair<double>`,只有`Pair<Double>`。
##### (3) 类型查询
在虚拟机中，对象总有一个特定非泛型类型，即原始类型，所以当类型查询时，只产生原始类型。
```java
Pair<String> stringPair = ...;
Pair<Double> doublePair = ...;
if(stringPair.getClass == doublePair.getClass) //总是true,都是Pair.class

if(doublePair instanceof Pair<String>) //实际上只是检测是不是任意类型的Pair
if(doublePair instanceof Pair<T>) //实际上只是检测是不是任意类型的Pair
```
##### (4) 不能创建参数化类型的数组

- 只能声明`List<String>[]`形式的数组，但不能创建`ArrayList<String>[10]`这样的对象。
- 可以声明通配类型的数组，然后进行强制转换
```java
List<String>[] list = (List<String>[]) new ArrayList<?>[10];
```
##### (5) 泛型类型的继承规则
虽然`Manager`是`Employee`的子类，但`Pair<Manager>`和`Pair<Employee>`却没有任何关系。

#### 4.通配符类型
- 概念:通配符类型中，允许参数类型变化。
```java
Pair<? extends Employee> //子类型限定通配符
Pair<? super Employee> //父类型限定通配符
Pair<?> //无限定通配符 个人认为相当于Pair<? extends Object>
```










