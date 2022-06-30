package top.zhao.rpc.test;


import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.provider.ServiceProvider;
import top.zhao.rpc.provider.ServiceProviderImpl;
import top.zhao.rpc.transport.RpcServer;
import top.zhao.rpc.transport.socket.server.SocketRpcServer;

/**
 * 测试用服务端
 * @author xiaozhao
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceProvider serviceRegistry = new ServiceProviderImpl();
        serviceRegistry.addServiceProvider(helloService, HelloService.class);
        RpcServer rpcServer = new SocketRpcServer("127.0.0.1",9000);
        rpcServer.start();
    }

}
