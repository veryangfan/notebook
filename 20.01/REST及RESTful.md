## REST及RESTful相关概念
>REST,即Representational State Transfer的缩写，翻译为中文就是‘表述性状态转移’。
### URL与URI
> **URI** = Universal Resource Identifier 统一资源标志符，用来标识抽象或物理资源的一个字符串。 
> **URL** = Universal Resource Locator 统一资源定位符，一种定位资源的主要访问机制的字符串，
  一个标准的URL必须包括：protocol、host、port、path、parameter。 
> **URN** = Universal Resource Name 统一资源名称，通过特定命名空间中的唯一名称或ID来标识资源。

![图片说明](https://uploadfiles.nowcoder.com/images/20190827/303744_1566913638867_600AFCC20717E60308CE583BB8DAF047 "图片标题") 

简单来说，就是一个URI可以表示一个资源，而Restful API正是基于而进行设计。
### http请求方式 
常用的http请求方式大概有如下几种：
- get
- post
- put
- patch
- delete

``
还有一些不是特别常用的请求方式（与REST无关，作为补充记录），如head\options
``

### REST
回到开头的引用段落看
>REST,即Representational State Transfer的缩写，翻译为中文就是‘表述性状态转移’。

现在我们来理解这句话。首先确定服务器上的对应资源，将每一个资源设置一个URI与之对应，配上http不同的请求方式，这是一种面向资源的操作。
在RESTful风格中，http的请求方式对应的使用场景如下：
- get 从服务器获取资源
- post 在服务器上新建资源
- put 修改服务器上的资源（提供完整数据，具有幂等性）
- patch 修改服务器上的资源（仅提供修改数据）
- delete 删除服务器上的资源

如：
- GET /school/student/{id} ———— 从服务器上获取一名学生的信息
- GET /school/student ———— 从服务器上获取所有学生的信息
- POST /school/student ————— 在服务器上新增一名学生信息
- PUT /school/student ————— 在服务器上修改学生信息
- DELETE /school/student/{id} ———— 删除一名学生信息
### RESTful API的设计
通常来讲，符合REST原则设计的接口，就可以称其为RESTful风格的接口。
但在实际应用中，有时候也未必严格遵守REST原则，例如常见的情况，因参数果多或者参数中含有保密信息，所以从服务器获取信息时也常常使用post请求方式。RESTful api的设计相对更加灵活，他不是一个原则性的东西，只是一种设计风格。
关于put为什么是幂等的：put请求在修改资源时，传的参数时资源表现的全部属性，所以无论执行多少次，执行结果都是一致的，而post和patch则不同，例如patch只传修改的属性，而当数据表中有一条属性为`更新时间`，此时每次patch的执行结果必然是不同的。这也是REST为什么选则put作为修改资源的请求方式，而不是post或者patch的原因。
记录下目前遇到的原则：
- 在设计过程中，接口字符串不包含动词。这个很好理解，URI记录的是服务器上的资源，既然是资源，就应该是名词，所以出现东西是不应该的。

参考:
[百度](http://www.baidu.com)

