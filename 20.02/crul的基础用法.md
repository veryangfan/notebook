# curl命令
- curl命令常用来做http请求。c表示client，url就是URL
- curl命令在Linux和mac系统下自带，window下建议下载git for windows
- 下面只是一些常用的参数，更多可以查阅帮助。

```bash
# 直接接一个网址
$ curl https://www.baidu.com

# -s 去掉计算的进度条
$ curl https://www.baidu.com -s

# -o 将返回的结果以文件的方式下载下来
$ curl https://www.baidu.com -s -o 1.txt
$ curl https://www.baidu.com -s -o 2.txt

# -H 指定请求头 如 "a:b"
$ curl localhost:88 -s -H "a:b"

# -X 指定请求方法,注意，请求方法大写
$ curl localhost:88 -s -H "a:b" -X POST

# -d 指定请求内容,默认以表单的形式提交，请求头中会自动添加'content-length': '6'和'content-type': 'application/x-www-form-urlencoded'
$ curl localhost:88 -s -H "a:b" -X POST -d "age=22"

# 还可以添加参数querystring
$ curl localhost:88?name=kk -s -H "a:b" -X POST -d "age=22"

# 也可以在请求头中自己指定请求内容的提交形式，比如改成json
$ curl localhost:88 -s -H "Content-Type:application/json" -X POST -d "{\"age\":22}"

# 帮助
$ curl -h

```

- 总结：参数注意区分大小写