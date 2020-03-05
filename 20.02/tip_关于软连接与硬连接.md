# 关于软硬连接与inode
- Linux将文件存储分为两个部分，用户数据(userdata)与元数据(metadata)

**用户数据**：即文件数据块 (data block)，数据块是记录文件真实内容的地方；
**元数据**： 则是文件的附加属性，如文件大小、创建时间、所有者等信息。
在 Linux 中，元数据中的 inode 号才是文件的唯一标识而非文件名。文件名仅是为了方便人们的记忆和使用，系统或程序通过 inode 号寻找正确的文件数据块。

![程序通过文件名获取文件内容的过程](https://note.obs.cn-north-4.myhuaweicloud.com/Snipaste_2020-03-03_20-08-30.png)

inode 是文件元数据的一部分,主要存储文件类型，权限，拥有者，时间（ctime,atime），连接数（一个文件名只能对应一个inode,但一个inode可能被多个连接指向了）。但并不包含文件名，inode 号即索引节点号。

可以使用`ls -i`查看inode号，`df -i`查看inode使用情况
```bash
[root@yangfan test]# ls
demo.test
[root@yangfan test]# ls -i
2359553 demo.test
```

- 硬连接，软连接

为解决文件的共享使用，Linux 系统引入了两种链接：硬链接 (hard link) 与软链接（又称符号链接，即 soft link 或 symbolic link）。

链接为 Linux 系统解决了文件的共享使用，还带来了隐藏文件路径、增加权限安全及节省存储等好处。

**若一个 inode 号对应多个文件名，则称这些文件为硬链接**。换言之，硬链接就是同一个文件使用了多个别名。硬链接可由命令 link 或 ln 创建。

软链接与硬链接不同，若**文件用户数据块中存放的内容是另一文件的路径名的指向**，则该文件就是软连接。
软链接就是一个普通文件，只是数据块内容有点特殊。软链接有着自己的 inode 号以及用户数据块。

![程序通过文件名获取文件内容的过程](https://note.obs.cn-north-4.myhuaweicloud.com/Snipaste_2020-03-03_20-33-04.png)

```bash
# 创建一个文本文件，内容为hello
[root@yangfan test]# touch test.txt
[root@yangfan test]# echo "hello" >>test.txt 
[root@yangfan test]# cat test.txt 
hello
# 创建硬连接
[root@yangfan test]# ln test.txt hardlink
[root@yangfan test]# ls
hardlink  test.txt
[root@yangfan test]# cat hardlink 
hello
# 创建软连接
[root@yangfan test]# ln -s test.txt softlink
[root@yangfan test]# ls
hardlink  softlink  test.txt
[root@yangfan test]# cat softlink 
hello
# 可以看出，硬链接与文件inode相同，软连接则不同
[root@yangfan test]# ls -i
2359547 hardlink  2359553 softlink  2359547 test.txt

```

所以创建硬链接时，连接计数会增加+1
创建软链接时，链接计数 i_nlink 不会增加；