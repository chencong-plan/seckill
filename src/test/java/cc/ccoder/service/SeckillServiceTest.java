package cc.ccoder.service;

import cc.ccoder.dto.Exposer;
import cc.ccoder.dto.SeckillExecution;
import cc.ccoder.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author chencong , Created in 2018/1/9 22:05
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-dao.xml"})
public class SeckillServiceTest implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        Long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {
        Long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        Long id = 1000L;
        Long userPhone = 13612312312L;
        String md5="";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
        logger.info("result={}",seckillExecution);

    }

}