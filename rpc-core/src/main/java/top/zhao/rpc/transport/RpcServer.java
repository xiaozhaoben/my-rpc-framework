package top.zhao.rpc.transport;

import top.zhao.rpc.serializer.CommonSerializer;

/**
 * 服务端通用接口
 *
 *@author xiaozhao
 */
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);

}
