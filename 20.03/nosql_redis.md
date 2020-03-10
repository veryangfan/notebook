# Redis

## 1. 数据类型
Redis数据库里面每个键值对（key-value）都是由对象组成的  
- 数据库的键总是一个字符串对象
- 数据库键对应的值可以是以下任意一种
    1. 字符串对象 string object
    2. 列表对象 list object
    3. 哈希对象 hash object
    4. 集合对象 set object
    5. 有序集合对象 sorted set object

### 基本命令
1. KEYS pattern
KEYS pattern 获得符合规则的键名列表
- *匹配一个或多个
-  ？匹配一个
- [] 匹配范围 如a[b-c]匹配"ab"、"ac"
- \x 匹配转义字符，如\?则匹配问号 
```bash
redis>KEYS *
 1)  "key"
 2)  "key2"
 3)  "key1"
```

2. EXISTS key
如果键存在则返回1，否则返回0
```bash
redis>EXISTS key
"1"
```

3. DEL key1 key2
删除一个或者多个键，返回值为键的个数
```bash
redis>DEL key1 key2
"2"
```

4. TYPE key
用来获取键值的数据类型
```bash
redis>TYPE key
"string"
```

### 1.1 字符串string
字符串类型是Redis最基本的数据类型，能存储任何形式的字符串，包括二进制数据。
字符串类型是其他4种数据类型的基础，其他数据类型只是组织字符串的形式不同。
用于：可以存储邮箱、JSON化的一个对象，甚至是一张图片。一个字符串类型允许存储的最大容量是512M  

#### 取值与赋值
SET key val  
GET key

```
redis>SET name "liming"
redis>GET name
```

####　其他操作
INCR key
字符串可以存储任何类型的数据，但当存储的字符串是整形时，INCR key可以使得key对应的value递增+1
操作成功会返回+1后得到的结果，失败会返回0

同理还有以下命令
```bash
INCRBY key i //+i
DERC key //-1
DERCBY key i //-i
INCRBYFLOAT key i //+浮点数i
STRLEN key //字符串长度
MSET key1 val1 key2 value2 //批量操作
MGET key1 key2 //字符串长度
```

### 1.2 散列类型 hash
hash是一种键值存储结构，字段field必须为string。
每个key最多可以存储2^32-1个字段
用于：散列类型比较适合存储对象。
key -- field -- value

#### 取值与赋值
```bash
HSET key field value
HGET key field
HMSET key  field1 value1 field2 value2
HMGET key  field1 field2 //返回的是一个字符串组成的列表
HGETALL key
```

#### 其他操作
```
HEXISTS key field //存在返回1，不存在返回0
HSETNX key filed val //当字段不存在时赋值，存在则不进行任何操作
INCRBY key field i //+i
HDEL key field1 field2
HKEYS key //取出key对应的全部field
HVALS key //取出key对应的全部value
```

### 1.3 列表类型 list
列表类型可以存储一个**有序的字符串列表**，常用的操作是向列表两端添加元素，或者获取列表的某一个片段
每个key最多可以存储2^32-1个字符串
列表类型内部是双向链表实现的
用于：双十一排队系统，选出下单第100到200的人免单；存储贴吧楼评论等

#### 取值与赋值
```bash
LPUSH key1 val1 key2 val2
RPUSH key1 val1 key2 val2
LPOP key
RPOP key
```
#### 其他操作
```bash
LLEN key //获取元素个数
LRANGE key start stop //获得列表片段（包含两端），Redis列表起始索引为0
LREM key count value //删除列表中前count个 值为value的元素
//count > 0时，LREM命令会从左边，删除列表中前count个 值为value的元素
//count < 0时，LREM命令会从右边，删除列表中前|count|个 值为value的元素
//count = 0时，LREM命令会删除列表中所有值为value的元素
```
```bash
LINDX key index //获得指定索引的元素值
LSET key index //设置指定索引的元素值
```
```bash
LTRIM key start end 删除指定索引之外的所有元素
```
```bash
//从左到右查找值为pivot的元素，插入其前后
LINSERT key BEFORE pivot value
LINSERT key AFTER pivot value
```

### 1.4 集合 set

集合内元素满足**无序性、唯一性**，
每个key最多可以存储2^32-1个字符串
用于：存储博客的标签（无序且唯一）

#### 取值与赋值
```bash
SADD key1 member1 member2
SMEMBERS key //获取集合中所有元素
```
```bash
SREM key member1 member2 //删除元素
SISMEMBER key member //判断元素是否在集合中，是1否0
```

#### 其他操作
```bash
SDIFF setAkey setBkey setCkey //求差集 先setA-setB-setC
SINTER setAkey setBkey setCkey //求交集
SUNION setAkey setBkey setCkey //求并集
```

```bash
SCARD key //获得元素个数
SPOP key //从集合中随机弹出删除一个元素
SRANDMEMBER key //随机获取一个元素
SRANDMEMBER key count //随机获取多个元素
//count > 0 随机获得count个不重复的元素
//count < 0 随机获得count个元素，可能存在重复
```


### 1.5 有序集合 sorted set

看起来和列表list很像，但有着很大的区别，导致应用场景也不同。  
（1）list是通过双向链表实现的，不利于查找；而zset是通过散列表和跳表实现的，查找速度更快  
（2）列表中不能调整某个元素的位置，而有序集合可以  
（3）有序集合更耗费内存  

#### 取值与赋值
```bash
ZADD key score member
//添加数据，存入后会自动按照从小到大排序
redis>ZADD scoreboard 89 Tom 67 Peter 100 David
"3"
//修改数据
redis>ZADD scoreboard 76 Tom
"0"
//还可以存储浮点型
redis>ZADD test 1.5 Tom
"1"
```
```bash
ZSCORE key member
//
redis>ZSCORE scoreboard Tom
"76"
redis>
```
```bash
//获得索引在某个范围内的元素 
//索引都是从0开始，负数代表从后向前查找（-1表示最后一个元素）
ZRANGE key start stop [WITHSCORES]    //按照分数从小到大的顺序
ZREVRANGE key start stop [WITHSCORES]   //按照分数从大到小的顺序
```
```bash
//获得分数在某个区间范围内的元素
ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]

redis>ZRANGESCORE scoreboard 80 100 
redis>ZRANGESCORE scoreboard 80 (100    //不包含100
redis>ZRANGESCORE scoreboard (80 +inf //查询高于80分的名单(+inf表示正无穷)
//高于60分的从第二个人开始的三个人
redis>ZRANGESCORE scoreboard 60 +inf LIMIT 1 3 
```

#### 其他操作

```bash
ZINCRBY key i member //+i
ZCARD key  //获得集合中元素的数量
ZCOUNT key min max //获得集合中范围内元素的数量
ZREM key member1 member2 //删除元素
ZREMRANGE key start stop //按索引删除一段元素
ZREMRANGEBYSCORE key min max //按区间范围删除元素
ZRANK key member //获得元素的排名(分数最小的元素排名为0)
ZRERANK key member //获得元素的排名(分数最大的元素排名为0)
```
```bash
//计算交集(记住可以求交集)
ZINTERSTORE ...
```