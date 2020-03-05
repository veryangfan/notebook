> 作为一个深度工具癖患者，低头写代码的同时，也该抽时间熟悉一下IDE，下面的内容都是使用过程中最常用且最基本的入门使用说明，务必熟练掌握。此外还可以美化一下idea, windows的字体渲染效果太差了，希望以后能努力找到给配MBP的工作。

![图片说明](https://uploadfiles.nowcoder.com/images/20190826/303744_1566833076680_4CD4F85B56F04F7C6103D663103AD396 "图片标题") 
``` 
1.本人使用版本为IntelliJ IDEA 2019.4(Ultimate Edtion) 教育版，学生可以使用教育邮箱免费申请；
2.本文长期更新，只记录自己觉得实用并适合自己的小tip；
3.下面链接为课程(IntelliJ IDEA神器使用技巧),只看了一小部分，希望有时间可以继续学习；
```

###### 课程入口：[IntelliJ IDEA神器使用技巧](https://www.imooc.com/learn/924)

---
### 1. 快捷键
#### 编写时使用
- Ctrl + Alt + [ 或 ] ———— 多个项目左/右切换
- Alt + Enter —————— 智能键，有问题就敲这个试试(导包，修正，补全返回值)
- Crtl + 鼠标左键 —————— controller跳到Service
- Crtl + Alt +鼠标左键 —————— controller 直接跳转到ServiceImpl
- Ctrl + / ——————注释，解除注释y
- Ctrl + ←或→ —————— 智能移动光标，可以一次切换一个单词
- Ctrl + w —————— 选中（可以双击选中）代码后，智能向外拓展
- Crtl + z ——————撤销
- Crtl + Shift + z —————— 向前，取消撤销，推荐使用（等同于Ctrl + y）
- Alt + Insert —————— 生成实体类的set/get/toString/构造函数
- Ctrl + D —————— 将当前行复制到下一行
- Ctrl + x —————— 删除整行（不选中，光标放在该行）/ 选中剪切
- Crtl + Alt + t —————— Surround with(if/try...catch等)
- Ctrl + Alt + l —————— 格式化代码
- Alt + ↑ 或 ↓ —————— 方法之间快速切换

#### 调试时使用
- Ctrl + F8 —————— 光标所在行 添加断点/移除断点
- F8 ———————— 执行下一行
- F9 ———————— 执行到下个断点
- Alt + F9 —————— 执行到光标处\

>除此之外，还有些执行服务的快捷键，暂时没用习惯

- Ctrl + F9 —————— Build Project
- Shift + F10 —————— run
- Shift + F9 —————— Debug

#### 设置时使用
- Ctrl + Shift +s —————— 设置，进入后可搜索（如在搜索框输入maven/font）
- 双击Shift —————— Search Everything

### 2. Git 图形界面
IntelliJ IDEA和很多IDE一样，内置了很多应用。其中内置的Git图形化界面可以说是非常好用，完全可以满足工作中的更新及提交代码等简单需求，更强大的图形界面也可以选则小乌龟（TortoiseGit）。
- 拉取代码pull —————— 选中项目—>右键Git—>Repository—>pull
- 提交代码到本地commit —————— 选中项目—>右键Git—> Commit Directory
- 推送push —————— 选中项目—>右键Git—>Repository—>push
- Alt + 9 —————— 打开 9：Version Control, 可以查看内容及日志
- 忽略文件 —————— 一直没找到特别优雅的方式，暂时使用该[博客](https://blog.csdn.net/dreamer23/article/details/83615461)的方法

### 3. 代码优化
- 重构与抽取等 （待学习...）
                   
        
---
``
暂时就用到这么多，以后会再补充记录
``
please !!













