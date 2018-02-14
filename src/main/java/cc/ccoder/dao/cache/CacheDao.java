package cc.ccoder.dao.cache;

import cc.ccoder.dao.SeckillDao;
import cc.ccoder.entity.Seckill;
import cc.ccoder.util.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义CacheManager缓存方法插入和修改方法，获取数据时从缓存当中查询数据
 *
 * @author :  chencong
 * @date :  2018/2/14 19:46
 * Package   :  cc.ccoder.dao.cache
 */
public class CacheDao {

    @Autowired
    private SeckillDao seckillDao;

    /**
     * 将seckill数据存入缓存当中
     *
     * @param seckillId seckillId所对应的seckill对象
     * @return 返回seckill对象
     */
    public Seckill getSeckill(Long seckillId) {
        Map params = new HashMap();
        params.put("seckillId", seckillId);
        Seckill seckill = CacheManager.getData(seckillId + "", new CacheManager.Load<Seckill>() {
            @Override
            public Seckill load(Map params) {
                return seckillDao.queryById((Long) params.get("seckillId"));
            }
        }, CacheManager.ALWAYS_ACTIVE, params);
        return seckill;
    }
}
