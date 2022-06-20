package top.zhao.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zhao.rpc.enums.ResponseCode;

import java.io.Serializable;

/**
 * 提供者执行完方法后向消费者返回
 *
 *@author xiaozhao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer statusCode;
    /**
     * 响应状态补充信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }
}
