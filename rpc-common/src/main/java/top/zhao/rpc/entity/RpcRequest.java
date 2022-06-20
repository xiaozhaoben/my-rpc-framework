package top.zhao.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消费者向提供者调用方法的请求
 *
 *@author xiaozhao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 接口名
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 调用方法的参数
     */
    private Object[] parameters;
    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;

}
