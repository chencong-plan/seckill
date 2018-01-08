package cc.ccoder.dao;

import cc.ccoder.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * @author : ChenCong
 * @date : Created in 11:08 2018/1/8
 */
public interface SeckillDao {

    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1,表示更新的行数
     */
    int reduceNumber(Long seckillId, Date killTime);

    /**
     * 查询库存
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(Long seckillId);


    /**
     * 根据偏移量查询商品信息，分页
     *
     * @param offset 偏移量（当前页数）
     * @param limit  偏移量之后的行数(大小)
     * @return
     */
    List<Seckill> queryAll(int offset, int limit);
}
