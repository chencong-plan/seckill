## Java高并发秒杀系统API

### 技能总结

#### 联合主键，避免重复秒杀
```sql
 PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
```
在这里使用的是秒杀商品id+用户手机作为秒杀成功的一个联合主键。当用户使用该手机+秒杀同一件商品时候从数据库层面来说就是不允许的。

可以从单元测试打印的log来查看。
```java
 @Test
    public void insertSuccessKilled() {
        Long id = 1000L;
        Long phone = 15212345678L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount:"+insertCount);
    }
```
通过用户使用手机号为`15212345678`来多次抢购秒杀`1000`号商品时，查看数据库生效行数。
- 第一次 `insertCount:1`
- 第二次 `insertCount:0`

### 异常信息记录

#### 控制台mapper异常

```log
org.apache.ibatis.exceptions.PersistenceException:   
### Error building SqlSession.  
### The error may exist in resources/mapper/SeckillDao.xml  
### Cause: org.apache.ibatis.builder.BuilderException: Error parsing SQL Mapper Configuration. Cause: java.io.IOException: Could not find resource resources/mapper/SeckillDao.xml   
```
出现了上述问题后，主要是由于这样几个问题
- 出现这个问题大多数都是找不到映射文件，这和没有遵循mybatis的mapper代理配置规范有关，对于我这个问题仔细看java.io.IOException:Could not find resource   
  resources/mapper下的seckillDao.xml,就是文件读写出现问题，系统找不到这个文件，需要检查，mapper接口与映射的mapper.xml 的命名是否一致，是否在同一目录下。  
- 如果仍然存在异常，主要从这几个方面解决
  + 在XXXMapper.xml的配置文件当中namespace是否填写完整
  + dao层接口当中方法名称是否和mapper.xml当中SQL语句id保持一致
  + dao层接口中参数名称 类型是否和mapper.xml当中parameterType所指定类型是否一致
  + dao层接口中参数名称 类型是否和mapper.xml当中resultMap或者resultType保持一致

#### dao层接口和xml之间多个参数问题
```log
Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
```
出现上述问题，可能是dao层接口当中我们使用了多个参数，然后在xml的配置文件当中直接使用这个参数名称。就会出现原先java当中参数名称不存在的情况。
因为在java当中是不保存形参记录的。例如：queryAll(int offset,int limit) ==》 queryAll(arg0,arg1) 那么再次在xml当中使用`offset`这样一个参数名称就会出现错误了。

这样的情况肯定是有方法解决的。使用注解`@Param`指定每一个参数名称.

修改之后如下所示：
```java
List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
```


