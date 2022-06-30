package top.zhao.rpc.exception;

/**
 * 序列化异常
 * @author xiaozhao
 */
public class SerializeException extends RuntimeException{
    public SerializeException(String msg) {
        super(msg);
    }
}
