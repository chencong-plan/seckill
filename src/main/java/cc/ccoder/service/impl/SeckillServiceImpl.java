package cc.ccoder.service.impl;

import cc.ccoder.dao.SeckillDao;
import cc.ccoder.dao.SuccessKilledDao;
import cc.ccoder.dto.Exposer;
import cc.ccoder.dto.SeckillExecution;
import cc.ccoder.entity.Seckill;
import cc.ccoder.entity.SuccessKilled;
import cc.ccoder.enums.SeckillStatEnum;
import cc.ccoder.exception.RepeatKillException;
import cc.ccoder.exception.SeckillCloseException;
import cc.ccoder.exception.SeckillException;
import cc.ccoder.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chencong , Created in 2018/1/9 20:29
 */
@Service
public class SeckillServiceImpl implements SeckillService,Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用户混淆MD5
     */
    private final String slat = "123456gfdddfdgdsvby54t4rwsd7895%&%#^$^$^%$(*╹▽╹*)";


    /**
     * 注入service依赖
     */

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 5);
    }

    @Override
    public Seckill getById(Long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(Long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        // 系统当前时间
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            // 秒杀还没有开始 或者 秒杀结束
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        // 转换特定字符串的过程 不可逆的过程
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 生成md5
     *
     * @param seckillId
     * @return
     */
    private String getMd5(Long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点：
     * 1、开发团队达成一致约定：明确标注事务方法的编程风格。
     * 2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作RPC/HTTP请求/或者剥离到事务方法外部
     * 3、不是所有方法都需要事务，如果只有一条修改操作，只读操作不需要事务控制
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(Long seckillId, Long userPhone, String md5) throws RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill date rewrite");
        }
        // 执行秒杀逻辑 减库存 + 记录购买行为
        Date nowTime = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
        try {
            if (updateCount <= 0) {
                // 没有更新记录 秒杀结束了
                throw new SeckillCloseException("seckill is closed");
            } else {
                // 减库存成功了 记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                // 唯一记录，联合主键:seckillId ,userPhone
                if (insertCount <= 0) {
                    // 重复秒杀
                    throw new RepeatKillException("seckill repeated");
                } else {
                    // 秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e) {
            throw e;
        } catch (RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 所有编译异常转换成运行时期异常
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
