package cc.ccoder.exception;

import cc.ccoder.dto.SeckillExecution;

/**
 * @author : ChenCong
 * @date : Created in 14:11 2018/1/9
 * 重复秒杀异常，运行期异常
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
