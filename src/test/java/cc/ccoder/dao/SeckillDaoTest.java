package cc.ccoder.dao;

import cc.ccoder.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author : ChenCong
 * @date : Created in 13:51 2018/1/8
 * 1、配置Spring和Junit整合，Junit启动时候加载springIOC容器
 * 2、spring-test Junit方便做测试
 */
// Junit运行时候加载spring-ioc容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉Junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // 注入dao实现
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L,killTime );
        System.out.println(updateCount);
    }

    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }
}