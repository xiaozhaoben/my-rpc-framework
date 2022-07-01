package top.zhao.rpc.test;

import top.zhao.rpc.api.HelloEntity;
import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.loadbalancer.RandomLoadBalancer;
import top.zhao.rpc.serializer.KryoSerializer;
import top.zhao.rpc.transport.RpcClient;
import top.zhao.rpc.transport.netty.client.NettyClient;
import top.zhao.rpc.transport.socket.client.RpcClientProxy;
import top.zhao.rpc.transport.socket.client.SocketRpcClient;

/**
 *  测试用客户端
 *
 * @author xiaozhao
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(0, new RandomLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloEntity helloEntity = new HelloEntity(1, "爱你");
        for(int i = 0; i < 10; i ++) {
            String res = helloService.hello(helloEntity);
            System.out.println(res);
        }
    }

}
