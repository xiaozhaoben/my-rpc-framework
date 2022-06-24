package top.zhao.rpc.test;


import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.registry.DefaultServiceRegistry;
import top.zhao.rpc.registry.ServiceRegistry;
import top.zhao.rpc.server.RpcServer;

/**
 * 测试用服务端
 * @author xiaozhao
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.registry(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);
    }

}
