# SQL优化面试题

## 一、针对SQL语句的优化
1. 查询语句中尽量不适用 *

2. 尽量减少子查询，使用关联查询（left join，right join，inner join）替代

3. 减少使用IN或者NOT IN，使用exists，not exists或者关联查询语句替代

4. 使用union或者union all代替or查询（确认没有重复数据或者不用剔除重复数据时，union all性能更好，解释器不用再扫描一遍表挑选重复）

5. 建表的时候能使用数字类型字段就不要使用字符串，数字类型的字段作为查询条件比字符串快。

6. 那些能过滤掉最大数量记录的条件必须写在where子句的最末尾。

7. 查询字段很多的情况下，慎用distinct关键字，会大大降低查询效率。

8. 定义字段时，尽量使用varchar而不是char。定义一个char[10]和varchar[10],如果存进去的是‘abcd’,那么char所占的长度依然为10，除了字符‘abcd’外，后面跟六个空格，而varchar就立马把长度变为4了，取数据的时候，char类型的要用trim()去掉多余的空格，而varchar是不需要的。char的存取数度还是要比varchar要快得多，因为其长度固定，方便程序的存储与查找；

## 二、索引

索引类型：唯一索引、组合索引、主键索引、普通索引  

优点：  
1. 建立索引的列可以保证行的唯一性，生成唯一的rowId
2. 建立索引可以有效缩短数据的检索时间
3. 建立索引可以加快表与表连接速度
4. 为用来排序或者是分组的字段添加索引，可以加速分组和排序速度  

缺点:   
1. 创建和维护索引需要时间成本，这个成本随着数据量的增加而加大
2. 创建和维护索引需要空间成本，每一条索引都要占用数据库的物理存储空间，数据量越大，占用空间也越大
3. 过多的索引会降低增删改的效率，因为每次增删改索引需要动态维护，导致时间变长。  

使索引失效的情况：(**重点**)

1. 使用like关键字模糊查询时，% 放在前面索引不起作用，只有“%”不在第一个位置，索引才会生效（like '%文'--索引不起作用）

2. 使用联合索引时，只有查询条件中使用了这些字段中的第一个字段，索引才会生效

3. 使用OR关键字的查询，查询语句的查询条件中只有OR关键字，且OR前后的两个条件中的列都是索引时，索引才会生效，否则索引不生效。

4. 尽量避免在where子句中使用!=或<>操作符，否则引擎将放弃使用索引而进行全表扫描。

5. 对查询进行优化，应尽量避免全表扫描，首先应考虑在where以及order by涉及的列上建立索引。

6. 应尽量避免在 where 子句中对字段进行表达式操作，这将导致引擎放弃使用索引而进行全表扫描。如：
select id from t where num/2=100 
应改为: 
select id from t where num=100*2 

7. 尽量避免在where子句中对字段进行函数操作,将导致引擎放弃使用索引而进行全表扫描。
8. 不要在 where 子句中的“=”左边进行函数、算术运算或其他表达式运算，否则系统将可能无法正确使用索引。       
9. 并不是所有的索引对查询都有效，sql是根据表中的数据来进行查询优化的，当索引列有大量数据重复时，sql查询不会去利用索引，如一表中有字段sex，male,female几乎各一半，那么即使在sex上建立了索引也对查询效率起不了作用。

10. 索引并不是越多越好，索引固然可以提高相应的 select 的效率，但同时也降低了 insert 及 update 的效率，因为 insert 或 update 时有可能会重建索引，所以怎样建索引需要慎重考虑，视具体情况而定。一个表的索引数最好不要超过6个，若太多则应考虑一些不常使用到的列上建的索引是否有必要。

11. 尽量使用数字型字段，若只含数值信息的字段尽量不要设计为字符型，这会降低查询和连接的性能，并会增加存储开销。这是因为引擎在处理查询和连接时会 逐个比较字符串中每一个字符，而对于数字型而言只需要比较一次就够了。

12. mysql查询只使用一个索引，因此如果where子句中已经使用了索引的话，那么order by中的列是不会使用索引的。因此数据库默认排序可以符合要求的情况下不要使用排序操作，尽量不要包含多个列的排序，如果需要最好给这些列建复合索引。

 

## 三、数据库引擎选择（MySQL中MyISAM和InnoDB）

1. 构成区别：

- InnoDB：基于磁盘的资源是InnoDB表空间数据文件和它的日志文件，InnoDB 表的大小只受限于操作系统文件的大小，一般为 2GB         

- MyISAM：每个MyISAM在磁盘上存储成三个文件。第一个文件的名字以表的名字开始，扩展名指出文件类型。.frm文件存储表定义。数据文件的扩展名为.MYD (MYData)。索引文件的扩展名是.MYI (MYIndex)。

2. 事物处理区别：

- InnoDB： InnoDB提供事务支持事务，外部键等高级数据库功能

- MyISAM：MyISAM类型的表强调的是性能，其执行数度比InnoDB类型更快，但是不提供事务支持

3. SELECT，UPDATE，INSERT，DELETE操作

InnoDB：

(1)如果你的数据执行大量的INSERT或UPDATE，出于性能方面的考虑，应该使用InnoDB表

(2)DELETE   FROM table时，InnoDB不会重新建立表，而是一行一行的删除。

(3)LOAD TABLE FROM MASTER操作对InnoDB是不起作用的，解决方法是首先把InnoDB表改成MyISAM表，导入数据后再改成InnoDB表，但是对于使用的额外的InnoDB特性（例如外键）的表不适用

MyISAM：如果执行大量的SELECT，MyISAM是更好的选择

4. 表的具体行数

InnoDB：InnoDB中不保存表的具体行数，也就是说，执行select count(*) from table时，InnoDB要扫描一遍整个表来计算有多少行

MyISAM：`select count(*) from table`, MyISAM只要简单的读出保存好的行数，注意的是，当count(*)语句包含where条件时，两种引擎的操作是一样的

5. 锁
InnoDB：支持表锁，行锁
MyISAM：表锁