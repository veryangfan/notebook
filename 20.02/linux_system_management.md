# Linux常见的一些系统管理的命令

## 网络
#### ifconfig 
可以显示已经激活的网卡信信息
```bash
[root@ecs-s6-medium-2-linux-20200211093034 ~]# ifconfig
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 192.168.0.82  netmask 255.255.255.0  broadcast 192.168.0.255
        inet6 fe80::f816:3eff:fe64:2c62  prefixlen 64  scopeid 0x20<link>
        ether fa:16:3e:64:2c:62  txqueuelen 1000  (Ethernet)
        RX packets 1142  bytes 104977 (102.5 KiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 1014  bytes 144543 (141.1 KiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        inet6 ::1  prefixlen 128  scopeid 0x10<host>
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 2  bytes 272 (272.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 2  bytes 272 (272.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

```

#### ping（网络层ICMP协议）
测试网络连通性
```bash
[root@ecs-s6-medium-2-linux-20200211093034 ~]# ping www.baidu.com
PING www.a.shifen.com (61.135.169.125) 56(84) bytes of data.
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=1 ttl=49 time=3.60 ms
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=2 ttl=49 time=3.54 ms
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=3 ttl=49 time=3.54 ms
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=4 ttl=49 time=3.53 ms
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=5 ttl=49 time=3.54 ms
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=6 ttl=49 time=3.62 ms
64 bytes from 61.135.169.125 (61.135.169.125): icmp_seq=7 ttl=49 time=3.47 ms
^C
--- www.a.shifen.com ping statistics ---
7 packets transmitted, 7 received, 0% packet loss, time 6009ms
rtt min/avg/max/mdev = 3.470/3.551/3.627/0.079 ms
```

## 系统信息

#### top

实时显示进程信息

![top](https://note.obs.cn-north-4.myhuaweicloud.com/top.jpg)
可以接grep进行筛选

![top-grep](https://note.obs.cn-north-4.myhuaweicloud.com/top.jpg)


#### ps

查看正在运行的进程

UID：程序被该 UID 所拥有
PID：就是这个程序的 ID 
PPID：则是其上级父程序的ID
C：CPU使用的资源百分比
STIME：系统启动时间
TTY：登入者的终端机位置
TIME：使用掉的CPU时间。
CMD：所下达的是什么指令

```bash
[root@ecs-s6-medium-2-linux-20200211093034 ~]# ps -ef
UID        PID  PPID  C STIME TTY          TIME CMD
root         1     0  0 14:27 ?        00:00:00 /usr/lib/systemd/systemd --switched-root --system --deserialize 22
root         2     0  0 14:27 ?        00:00:00 [kthreadd]
root         4     2  0 14:27 ?        00:00:00 [kworker/0:0H]
root         5     2  0 14:27 ?        00:00:00 [kworker/u2:0]
root         6     2  0 14:27 ?        00:00:00 [ksoftirqd/0]
root         7     2  0 14:27 ?        00:00:00 [migration/0]
root         8     2  0 14:27 ?        00:00:00 [rcu_bh]
root         9     2  0 14:27 ?        00:00:00 [rcu_sched]

[root@ecs-s6-medium-2-linux-20200211093034 ~]# ps -ef | grep ssh
UID        PID  PPID  C STIME TTY          TIME CMD
root      1430     1  0 14:27 ?        00:00:00 /usr/sbin/sshd -D
root      2251  1430  0 14:36 ?        00:00:00 sshd: root@pts/1
root      2383  2253  0 19:08 pts/1    00:00:00 grep --color=auto ssh

```
#### kill
终止进程
```bash
# kill [pid] 默认情况下，kill的参数是15,代表的信号为SIGTERM
# kill -15，这是告诉进程你需要被关闭，请自行停止运行并退出；
[root@ecs-s6-medium-2-linux-20200211093034 ~]# kill 12345
# kill [pid] 指定参数为9,代表的信号是SIGKILL,信号不能被捕获也不能被忽略
# kill -9表示强制杀死该进程，表示进程被终止，需要立即退出；
[root@ecs-s6-medium-2-linux-20200211093034 ~]# kill -9 12345
```

## 其他
#### ln 创建连接
```bash
# 文件inode中链接数会增加，只有链接数减为0时文件才真正被删除
ln file1 file2
#创建软连接
# -s(symbol)为符号链接，仅仅是引用路径
# 相比于硬链接最大特点是可以跨文件系统
# 类似于Windows创建快捷方式，实际文件删除则链接失效
ln -s file1 file2
```


#### du df
du，disk usage,是通过搜索文件来计算每个文件的大小然后累加，du能看到的文件只是一些当前存在的，没有被删除的。他计算的大小就是当前他认为存在的所有文件大小的累加和。
df，disk free，通过文件系统来快速获取空间大小的信息，当我们删除一个文件的时候，这个文件不是马上就在文件系统当中消失了，而是暂时消失了，当所有程序都不用时，才会根据OS的规则释放掉已经删除的文件， df记录的是通过文件系统获取到的文件的大小，他比du强的地方就是能够看到已经删除的文件，而且计算大小的时候，把这一部分的空间也加上了，更精确了。

`df -i` 可以查看inode使用情况
`df -h` 其中 -h 表示使用K，M，G的人性化形式显示


#### chmod
修改文件权限,三种权限rwx为读写执行，三组依次为[文件拥有者]、[相同组group的人]、[其他人]。

```bash
[root@yangfan test]# ll
total 8
-rw-r--r-- 2 root root 6 Mar  3 20:51 hardlink
lrwxrwxrwx 1 root root 8 Mar  3 20:53 softlink -> test.txt
-rw-r--r-- 2 root root 6 Mar  3 20:51 test.txt

# 可以对三种使用者设置权限，u(user, owner)，g(group)，o(other)
# 文件可以有三种权限，r(read)，w(write)，x(execute)
# 这里u+r表示文件所有者在原有基础上增加文件读取权限
# 这里777分别对应，u=7，g=7，o=7，具体数字含义自行google
chmod u+r file
chmod 777 file
```
 