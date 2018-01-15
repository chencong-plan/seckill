package cc.ccoder.dao;

/**
 * @author : ChenCong
 * @date : Created in 10:09 2018/1/15
 * vo 封装所有的数据结果，封装json结果
 */
public class SeckillResult<T> {

    private Boolean success;
    private T data;
    private String message;

    public SeckillResult() {
    }

    public SeckillResult(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public SeckillResult(Boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
