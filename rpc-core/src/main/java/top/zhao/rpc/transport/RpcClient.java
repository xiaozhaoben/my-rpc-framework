package top.zhao.rpc.transport;

import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.serializer.CommonSerializer;

/**
 * 客户端通用接口
 *
 *@author xiaozhao
 */
public interface RpcClient {

    Object sendRequest(RpcRequest request);

    void setSerializer(CommonSerializer serializer);

}
