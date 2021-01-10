# RPC
## RPC 定义

> 在分布式计算中，远程过程调用（remote procedure call,RPC）是指计算机程序使过程（子例程）在不同的地址空间（通常在共享网络的另一台计算机上）执行时，其编码方式就像是普通的（ 本地）过程调用，而无需程序员为远程交互明确编码细节。  ---维基百科

## 举个例子

![](https://note.obs.cn-north-4.myhuaweicloud.com/rpc%E7%A4%BA%E6%84%8F%E5%9B%BE.jpg)

1. 为什么要R远程？
这很简单，可能本机需要实现一个功能A，但A功能需要借助B来实现，B又不属于A这个功能范围内的事情，所以我们需要调用一个实现B功能的一个集群，去获取计算出来的这个结果。
2. RPC和HTTP之间的区别？
广义上讲，HTTP其实就是一种RPC，RPC是一种思想，HTTP只是实现RPC的一种方式；
狭义上将，有时候RPC指的是RPC的通信框架，比如gRPC,Thrift等

3. 内存-->传输
文本、byte[]
序列化、反序列化，如xml,json都是文本化的序列化，在比较高效的RPC框架可能会使用jdk的序列化方式，hadoop也自己封装了序列化方式，Hessian、Protobuf等。

4. 网络IO性能
比如Java中的RPC通信框架Dubbo，Dobbo中使用netty作为一个网络传输的一个框架实现，netty又是基于Java的NIO，NIO又使用了系统调用实现了如零拷贝、多路复用等，实现了IO性能的提升。

## gRPC

- 搭建一个demo

https://github.com/veryangfan/notebook/tree/master/21.01/grpcjava
