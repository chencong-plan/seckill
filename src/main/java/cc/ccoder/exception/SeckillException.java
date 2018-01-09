package cc.ccoder.exception;

/**
 * @author : ChenCong
 * @date : Created in 14:14 2018/1/9
 * 秒杀业务相关异常
 */
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
