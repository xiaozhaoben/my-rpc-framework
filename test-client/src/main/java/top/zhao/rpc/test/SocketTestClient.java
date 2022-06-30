package top.zhao.rpc.test;

import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.api.HelloEntity;
import top.zhao.rpc.transport.RpcClient;
import top.zhao.rpc.transport.socket.client.RpcClientProxy;
import top.zhao.rpc.transport.socket.client.SocketRpcClient;

/**
 *  测试用客户端
 *
 * @author xiaozhao
 */
public class SocketTestClient {

    public static void main(String[] args) {
        RpcClient client = new SocketRpcClient("127.0.0.1", 9000);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloEntity helloEntity = new HelloEntity(1, "爱你");
        String res = helloService.hello(helloEntity);
        System.out.println(res);
    }

}
