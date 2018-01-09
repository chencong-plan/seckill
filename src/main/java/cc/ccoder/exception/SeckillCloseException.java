package cc.ccoder.exception;

/**
 * @author : ChenCong
 * @date : Created in 14:13 2018/1/9
 * 秒杀关闭异常
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
