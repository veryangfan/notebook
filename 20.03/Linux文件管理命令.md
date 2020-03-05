# 常用的文件管理操作命令
这部分命令是最基础也是最常用的命令

- ~表示当前用户目录，/ 表示root根目录
- date 时间日期
- cal 日历
- pwd
- cd
- ls/ll/ls -l/ls-al 
- mikdir / rmdir
- cp / mv
- cat / diff / head / tail
- less / more 
- rm / rm -f / rm -r / rm -rf
- chmod
- more / less
- grep
grep的一些正则用法

开头^和结尾$
`grep a file.txt` 含a的内容  
`grep -v a file.txt` 不含a的内容  
`grep ^[a-z] file.txt` 小写字母开头  
`grep ^[^a-z] file.txt` 不以小写字母开头  
`grep ^[a-zA-Z] file.txt` 不以字母开头  
`grep ^a file.txt` 以a开头  
`grep a$ file.txt` 以a结尾  

任意一个字符. 与重复字符*  
`grep g..d file.txt` g和d之间有两个字母 如good  
`grep aaa* file.txt` 至少有两个a  





