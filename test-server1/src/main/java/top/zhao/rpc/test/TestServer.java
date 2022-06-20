package top.zhao.rpc.test;


import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.server.RpcServer;

/**
 * 测试用服务端
 * @author xiaozhao
 */
public class TestServer {

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        HelloService helloService = new HelloServiceImpl();
        rpcServer.register(helloService, 9000);
    }

}
