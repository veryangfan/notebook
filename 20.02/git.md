# git基本命令
记录一下玩[learingitbranching](https://learngitbranching.js.org/)过程中学到的命令行

## 分支操作

- `git branch -d branchname`
删除本地分支

- `git push origin --delete branchname`
删除远程分支

## git stash命令
- `git stash save "save message"`
暂存 + 备注

- `git stash list` 
查看stash了哪些存储

- `git stash pop`
命令恢复之前缓存的工作目录

- `git stash clear`
删除所有缓存的stash


