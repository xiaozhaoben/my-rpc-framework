package top.zhao.rpc.test;


import top.zhao.rpc.annotation.ServiceScan;
import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.provider.ServiceProvider;
import top.zhao.rpc.provider.ServiceProviderImpl;
import top.zhao.rpc.serializer.KryoSerializer;
import top.zhao.rpc.transport.RpcServer;
import top.zhao.rpc.transport.netty.server.NettyServer;

/**
 * 测试用服务端
 * @author xiaozhao
 */
@ServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer rpcServer = new NettyServer("127.0.0.1", 9999);
        rpcServer.start();
    }

}
