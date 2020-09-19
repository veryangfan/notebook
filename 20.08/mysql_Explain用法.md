>这个博客写的非常清楚：https://segmentfault.com/a/1190000008131735

# MySQL 性能优化Explain 使用分析

## EXPLAIN 输出格式

各列的含义如下:

- id: SELECT 查询的标识符. 每个 SELECT 都会自动分配一个唯一的标识符.
- select_type: SELECT 查询的类型.
- table: 查询的是哪个表，包括查询涉及的表或衍生表
- partitions: 匹配的分区
- type: join 类型
- possible_keys: 此次查询中可能选用的索引
- key: **此次查询中确切使用到的索引.**
- ref: 哪个字段或常数与 key 一起被使用
- rows: 显示此查询一共扫描了多少行. 这个是一个估计值.
- filtered: 表示此查询条件所过滤的数据的百分比
- extra: 额外的信息