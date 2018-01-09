package cc.ccoder.dto;

import cc.ccoder.entity.SuccessKilled;
import cc.ccoder.enums.SeckillStatEnum;

/**
 * @author : ChenCong
 * @date : Created in 14:08 2018/1/9
 * 封装执行秒杀执行后的操作
 */
public class SeckillExecution {

    private Long seckillId;

    /**
     * 秒杀执行结果状态
     */
    private Integer state;

    /**
     * 状态表示
     */
    private String stateInfo;

    /**
     * 秒杀成功对象
     */
    private SuccessKilled successKilled;

    public SeckillExecution() {
    }

    public SeckillExecution(Long seckillId, SeckillStatEnum statEnum ,SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution( SeckillStatEnum statEnum) {
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
