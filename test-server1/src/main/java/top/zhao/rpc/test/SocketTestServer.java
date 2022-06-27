package top.zhao.rpc.test;


import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.registry.DefaultServiceRegistry;
import top.zhao.rpc.registry.ServiceRegistry;
import top.zhao.rpc.transport.RpcServer;
import top.zhao.rpc.transport.socket.server.SocketRpcServer;

/**
 * 测试用服务端
 * @author xiaozhao
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new SocketRpcServer(serviceRegistry);
        rpcServer.start(9000);
    }

}
