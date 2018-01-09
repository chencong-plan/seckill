package cc.ccoder.service;

import cc.ccoder.dto.Exposer;
import cc.ccoder.dto.SeckillExecution;
import cc.ccoder.entity.Seckill;
import cc.ccoder.exception.RepeatKillException;
import cc.ccoder.exception.SeckillCloseException;
import java.util.List;

/**
 * @author : ChenCong
 * @date : Created in 17:38 2018/1/8
 * <p>
 * 业务接口：站在"使用者"的角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型(return类型/异常)
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(Long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出系统时间和秒杀时间
     *
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(Long seckillId);


    /**
     * 执行秒杀业务
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    SeckillExecution executeSeckill(Long  seckillId, Long userPhone, String md5)
        throws RepeatKillException,SeckillCloseException;
}
