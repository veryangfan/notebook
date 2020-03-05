## Linux入门之Vi
    
1. Linux Vi的使用

- 引言

首先，来考虑一下为什么Vi编辑器，这个问题一直困扰了我很长时间，因为在大学期间学习Linux操作系统时，使用的Linux发行版为Ubuntu,后来自己使用过程中选择的是CentOS 7版本，使用的内置编辑器通常为gedit，界面美观好用，这两个操作系统也是主流的发行版本。所以一度认为可以替代Vi。直到最近使用了SSH....天真，原来自己从未用过不带图形界面的Linux。

```
[yangfan@study work]$ gedit

(gedit:3479): Gtk-WARNING **: cannot open display: 
```

- Vi 使用方法

话不多说，看命令行.

(1) 新建文件时，直接进入编辑模式（Insert Mode），可以直接输入内容。输入完成后按【ESC】进入到一般命令模式(command mode)下，此时，输入【:】，进入命令行模式（command-line mode）,输入【:wq】即可保存退出，输入【:q】直接退出不保存。
```
[yangfan@study work]$ vi test.txt
hello Vi!
hello Linux!
hello world!
~                                                       
~                                                      
~                                                            
"test.txt" 3L, 36C  
```
(2)保存完成，查看是否保存成功。
```
[yangfan@study work]$ cat test.txt 
hello Vi!
hello Linux!
hello world!
[yangfan@study work]$ 
```
(3) 再次编辑时，继续用vi打开，此时为到一般命令模式，不可编辑，需要切换进入插入模式。

按【i】进入插入模式后，光标当前位置开始输入文字；  
按【a】进入插入模式后，是从当前光标所在位置的下一个位置开始输入文字；  
按【o】进入插入模式后，当前光标位置下一行，从行首开始输入文字。


(4) 当文件过长时，使用⬆、⬇查看比较慢，可以使用翻页查看，ctrl+f(forward), ctrl+b(backward)

----
完结撒花！


