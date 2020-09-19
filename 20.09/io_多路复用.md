# IO多路复用

## 为什么IO多路复用

假设我们要设计一个高性能的网络服务器，这个服务器可以供多个客户端同时进行连接，并且能处理请求。

假设我们使用多线程处理并发，但多线程需要上下文切换，这个过程其实是非常繁琐的，尤其是客户端连接非常多，上下文切换的代价非常高。所以我们把目光转回来，回到单线程处理大量的客户端连接。

假设有ABCDE请求连接服务器，则
```c
while(1){
    for( Fdx in (FdA~FdE)){
        if(Fdx 有数据){
            读Fdx;
            处理Fdx;
        }
    }
}
```

这个程序处理的方式性能还不错，但依然不够好，原因在于判断是否有数据的步骤是我们的程序判断的，这样性能是比较低的。我们看看select、poll、epoll是怎么做的。

比如大名鼎鼎的Redis和Nginx用的就是epoll，javaNIO在Linux下用的也是epoll.

## select

![select](https://note.obs.cn-north-4.myhuaweicloud.com/select.png)

select提升效率的主要方式是，将reset（是一个1024位的bitmap，标记io是否有数据）从用户态拷贝到内核态，让内核帮我们判断是否有数据，这样效率会提升。

## poll

![poll](https://note.obs.cn-north-4.myhuaweicloud.com/poll.png)

poll和select的方式原理实际上差不多，poll自己定义了一个结构体pollfd，里面有一个属性revents,当有数据来了的时候，将其置为为POLLIN,然后进行读数据处理数据。poll使用pollfds（pollfd数组，数据大小远大于1024）解决select 1024个io限制的问题。并且pollfds通过置位也可以恢复。


## epoll

![epoll](https://note.obs.cn-north-4.myhuaweicloud.com/epoll.png)

epoll的方式是将epfd这块内存设置为用户态和共享的，降低拷贝的开销。
此外，epoll的模式下，返回的nfds是一个整数，表示有几个io有数据，并且将有数据的io置于数组最前面，这样就可以实现O(1)的查找时间复杂度了。





## tip

- 用户态

- 内核态





