-- 数据库初始化脚本

-- 创建数据库
create database seckill;

-- 使用数据库
use seckill;

-- 在下面这个表当中的字段'start_time'是一个timestamp类型的，mysql5.7可能会出现invalid default value的异常，因此我就将其添加默认值 current_timestamp
-- 创建秒杀库存表
create table seckill(
  `seckill_id` bigint not null AUTO_INCREMENT COMMENT '商品库存id',
  `name` VARCHAR(120) not null comment '商品名称',
  `number` int not null comment '库存数量',
  `start_time` timestamp not null DEFAULT  current_timestamp COMMENT '秒杀开始时间',
  `end_time` timestamp not null default current_timestamp COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP not null DEFAULT  current_timestamp COMMENT '创建秒杀时间',
  primary key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time (end_time),
  KEY idx_create_time (create_time)
)ENGINE=innoDB AUTO_INCREMENT= 1000 DEFAULT  CHARSET =utf8 COMMENT ='秒杀库存表';

-- 初始化数据
INSERT into seckill(name,number ,start_time ,end_time)
values
  ('1000元秒杀iphone6',100,'2018-1-8 00:00:00','2018-1-9 00:00:00'),
  ('1800元秒杀ipad2',200,'2018-1-8 00:00:00','2018-1-9 00:00:00'),
  ('300元秒杀小米5',300,'2018-1-8 00:00:00','2018-1-9 00:00:00'),
  ('200元秒杀红米',200,'2018-1-8 00:00:00','2018-1-9 00:00:00'),
  ('1000元秒杀iphone8',400,'2018-1-8 00:00:00','2018-1-9 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
create table  success_killed (
`seckill_id` bigint not null COMMENT '秒杀商品id',
`user_phone` bigint not null comment '用户手机号',
`state` tinyint not null DEFAULT -1 COMMENT '状态表示：-1 无效 0 成功 1 已付款',
`create_time` timestamp not null comment '创建时间',
primary key (seckill_id,user_phone), /*联合主键*/
key idx_create_time (create_time)
)ENGINE=innoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表';

-- 连接数据库控制台