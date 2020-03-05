# 主要指令：find grep more cat tail head
## 1. find
用来查找文件，
### 基于文件名进行查找（ -name ）
比如，查找1.txt
```
[root@localhost yangfan]# mkdir demo
[root@localhost yangfan]# cd demo/
[root@localhost demo]# touch 1.txt
[root@localhost demo]# cd ..
[root@localhost yangfan]# pwd
/home/yangfan
[root@localhost yangfan]# find / -name 1.txt
# 这是一个官方的bug，是个空文件夹，不用管
find: ‘/run/user/1000/gvfs’: Permission denied
/home/yangfan/demo/1.txt
[root@localhost yangfan]# find / -name 1.txt -print0|xargs -0 ls -l
find: ‘/run/user/1000/gvfs’: Permission denied
-rw-r--r--. 1 root root 0 Oct 20 06:23 /home/yangfan/demo/1.txt
[root@localhost yangfan]# find . -name *.txt -print0|xargs -0 ls -l
```
### 根据文件大小进行查找（ -size ）

```
# 比如，查找当前目录下大于5M的文件
[root@localhost yangfan]# find . -size +5M
./.mozilla/firefox/sd4g0v9f.default/places.sqlite
[root@localhost yangfan]# find . -size +5M -print0|xargs -0 ls -l
-rw-r--r--. 1 yangfan yangfan 10485760 Oct 20 06:10 ./.mozilla/firefox/sd4g0v9f.default/places.sqlite

```
### 根据类型进行区分查找（ -type ）

-type f # 表示文件 file
-type d # 表示文件夹 dir

```
[root@localhost yangfan]# find / -name config
find: ‘/run/user/1000/gvfs’: Permission denied
/sys/devices/pci0000:00/0000:00:00.0/config
/sys/devices/pci0000:00/0000:00:01.0/config
# 中间还有很多
/usr/share/yelp/mathjax/config
/usr/src/kernels/3.10.0-693.el7.x86_64/include/config
/usr/src/kernels/3.10.0-693.el7.x86_64/scripts/config

# 文件和文件夹全都显示出来了
[root@localhost yangfan]# find / -name config -type d
find: ‘/run/user/1000/gvfs’: Permission denied
/sys/kernel/config
/usr/lib/python2.7/site-packages/firewall/config
/usr/lib64/python2.7/config
/usr/share/yelp/mathjax/config
/usr/src/kernels/3.10.0-693.el7.x86_64/include/config

```

### 根据修改时间进行区分查找（ -ctime -cmin -mtime -mmin -atime -amin）
-ctime :修改时间 /天
-cmin ：修改时间 /分钟
-mtime :修改时间 /天,不包括权限
-mmin :修改时间 /分钟不包括权限
-atime ：读取时间 /天
-amin ：读取时间 /分钟

```
# 1分钟之内修改过的文件
[root@localhost yangfan]# find . -cmin -1
```
### 根据文件夹的深度进行查找（ -maxdepth  -mindepth ）
查找最大深度为3的文件夹下的txt文档
```
[root@localhost yangfan]# find . -maxdepth 3 -name "*.txt"
./.cache/tracker/db-version.txt
./.cache/tracker/db-locale.txt
./.cache/tracker/parser-sha1.txt
./.cache/tracker/locale-for-miner-user-guides.txt
./.cache/tracker/locale-for-miner-apps.txt
./.cache/tracker/last-crawl.txt
./.cache/tracker/first-index.txt
./demo/1.txt
```

## 2. grep
在文件中进行查找 
```
[root@localhost demo]# grep "a" 1.txt
[root@localhost demo]# echo "aa" >>1.txt 
[root@localhost demo]# echo "bb" >>1.txt 
[root@localhost demo]# echo "bb" >>1.txt 
[root@localhost demo]# echo "bb" >>1.txt 
[root@localhost demo]# echo "aa" >>1.txt 
[root@localhost demo]# cat 1.txt
aa
bb
bb
bb
aa
[root@localhost demo]# grep "a" 1.txt
aa
aa
# 输出所在行号
[root@localhost demo]# grep  -n "a" 1.txt
1:aa
5:aa

# 输出前后行 -A (after) -B (before)
[root@localhost demo]# grep  -n "a" -A 2 1.txt
1:aa
2-bb
3-bb
--
5:aa

[root@localhost demo]# find . -maxdepth 3 -name "*.txt" -print0|xargs -0 grep -n "aa"
1:aa
5:aa

```

## 3. cat
```
# 查看文件内容
[root@localhost demo]# cat 1.txt 
aa
bb
bb
bb
aa
[root@localhost demo]# cat -n 1.txt 
     1	aa
     2	bb
     3	bb
     4	bb
     5	aa
```

## 4. more
当文件行数较多时（比如日志文件），加载全部比较费时间，此时可以使用more进行分页查看，然后使用【回车键】查看下一行，【空格键】查看下一页,【b】返回上一页，【q】退出
```
[root@localhost demo]# more 2.txt 
# 指定每一页的行数  -3 表示3行
[root@localhost demo]# more -3 2.txt 
aa
bb
cc

# 指定从第四行开始读
[root@localhost demo]# more -3 +4 2.txt 
dd
aa
dd

```

5. head tail
head 头
tail 尾

```
# 默认打印前十行
[root@localhost demo]# head 2.txt
aa
bb
cc
dd
aa
dd
ff
vvv
vv
ss
# 指定打印前三行
[root@localhost demo]# head -n 3 2.txt 
aa
bb
cc
# 指定打印最后三行
[root@localhost demo]# tail -n 3 2.txt 
sd
ss

# 持续监控文件的变动，在末尾新增/删除 
[root@localhost demo]# tail -f 2.txt 
aa
dfdsd
dd
bb
cc
kk
hh
sd
ss

```
> 假设要取 /demo/2.txt 的第11到15行
```
[root@localhost demo]# head -n 15 2.txt | tail -n 5
dfd
ee
sdfs
htyj
rtr

[root@localhost demo]# cat -n 2.txt | head -n 15 | tail -n 5
    11	dfd
    12	ee
    13	sdfs
    14	htyj
    15	rtr
```


## df du
df 查看磁盘使用情况，列出文件系统整体磁盘使用情况
du 查看目录下文件大小（常用在查看目录所占磁盘空间）
查看磁盘使用情况
```
[root@localhost demo]# df -h
Filesystem      Size  Used Avail Use% Mounted on
/dev/sda3        58G  3.4G   55G   6% /
devtmpfs        898M     0  898M   0% /dev
tmpfs           912M     0  912M   0% /dev/shm
tmpfs           912M  9.0M  903M   1% /run
tmpfs           912M     0  912M   0% /sys/fs/cgroup
/dev/sda1       297M  157M  141M  53% /boot
tmpfs           183M   28K  183M   1% /run/user/1000
tmpfs           183M     0  183M   0% /run/user/0

# 当后面接了目录时，会自动分析出该目录所在的磁盘分区，并将该分区容量显示出来
[root@localhost demo]# df -h .
Filesystem      Size  Used Avail Use% Mounted on
/dev/sda3        58G  3.4G   55G   6% /

# 查看路径深度不超过1的
[root@localhost yangfan]# du -h -d 1
13M	./.mozilla
13M	./.cache
104K	./.config
0	./Desktop
0	./Downloads
0	./Templates
0	./Public
0	./Documents
0	./Music
0	./Pictures
0	./Videos
304K	./.local
8.0K	./demo
26M	.

```

free
可以显示Linux系统中空闲的、已用的物理内存及swap内存,及被内核使用的buffer。
```
[root@localhost yangfan]# free
              total        used        free      shared  buff/cache   available
Mem:        1867024      820500      271800        9948      774724      784264
Swap:       2098172           0     2098172

```
>练习题目：输出当前路径 和 当前路径的子路径下所有的txt文件，要求大小超过1M，并且按照从大到小的顺序进行排序，输出前十个

```
先通过 find . -name '*.txt' -size +1M -type f 查看是否有大于1M的txt文件，没有的话就不用继续了
再通过 find . -name '*.txt' -size +1M -type f -print0|xargs -0 du -m|sort -nr|head -10
```