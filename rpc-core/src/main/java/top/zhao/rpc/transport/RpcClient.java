package top.zhao.rpc.transport;

import top.zhao.rpc.entity.RpcRequest;

/**
 * 客户端通用接口
 *
 *@author xiaozhao
 */
public interface RpcClient {

    Object sendRequest(RpcRequest request);

}
