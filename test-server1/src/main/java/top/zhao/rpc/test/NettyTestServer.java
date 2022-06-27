package top.zhao.rpc.test;


import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.provider.ServiceProvider;
import top.zhao.rpc.provider.ServiceProviderImpl;
import top.zhao.rpc.serializer.KryoSerializer;
import top.zhao.rpc.transport.netty.server.NettyServer;

/**
 * 测试用服务端
 * @author xiaozhao
 */
public class NettyTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProvider provider = new ServiceProviderImpl();
        provider.addServiceProvider(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new KryoSerializer());
        server.start(9999);
    }

}
