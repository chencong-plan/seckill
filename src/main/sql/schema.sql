-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
USE seckill;

-- 在下面这个表当中的字段'start_time'是一个timestamp类型的，mysql5.7可能会出现invalid default value的异常，因此我就将其添加默认值 current_timestamp
-- 创建秒杀库存表
CREATE TABLE seckill (
  `seckill_id`  BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '商品库存id',
  `name`        VARCHAR(120) NOT NULL
  COMMENT '商品名称',
  `number`      INT          NOT NULL
  COMMENT '库存数量',
  `start_time`  TIMESTAMP    NOT NULL DEFAULT current_timestamp
  COMMENT '秒杀开始时间',
  `end_time`    TIMESTAMP    NOT NULL DEFAULT current_timestamp
  COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp
  COMMENT '创建秒杀时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time (end_time),
  KEY idx_create_time (create_time)
)
  ENGINE = innoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT ='秒杀库存表';

-- 初始化数据
INSERT INTO seckill (name, number, start_time, end_time)
VALUES
  ('1000元秒杀iphone6', 100, '2018-1-8 00:00:00', '2018-1-9 00:00:00'),
  ('1800元秒杀ipad2', 200, '2018-1-8 00:00:00', '2018-1-9 00:00:00'),
  ('300元秒杀小米5', 300, '2018-1-8 00:00:00', '2018-1-9 00:00:00'),
  ('200元秒杀红米', 200, '2018-1-8 00:00:00', '2018-1-9 00:00:00'),
  ('1000元秒杀iphone8', 400, '2018-1-8 00:00:00', '2018-1-9 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed (
  `seckill_id`  BIGINT    NOT NULL
  COMMENT '秒杀商品id',
  `user_phone`  BIGINT    NOT NULL
  COMMENT '用户手机号',
  `state`       TINYINT   NOT NULL DEFAULT -1
  COMMENT '状态表示：-1 无效 0 成功 1 已付款',
  `create_time` TIMESTAMP NOT NULL
  COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  KEY idx_create_time (create_time)
)
  ENGINE = innoDB
  DEFAULT CHARSET = utf8
  COMMENT '秒杀成功明细表';

-- 连接数据库控制台