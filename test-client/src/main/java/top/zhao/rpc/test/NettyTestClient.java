package top.zhao.rpc.test;

import top.zhao.rpc.api.HelloEntity;
import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.api.SayService;
import top.zhao.rpc.loadbalancer.RandomLoadBalancer;
import top.zhao.rpc.serializer.KryoSerializer;
import top.zhao.rpc.transport.RpcClient;
import top.zhao.rpc.transport.netty.client.NettyClient;
import top.zhao.rpc.transport.socket.client.RpcClientProxy;
import top.zhao.rpc.transport.socket.client.SocketRpcClient;

import static top.zhao.rpc.serializer.CommonSerializer.DEFAULT_SERIALIZER;

/**
 *  测试用客户端
 *
 * @author xiaozhao
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient(DEFAULT_SERIALIZER, new RandomLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloEntity helloEntity = new HelloEntity(0, "爱你");
        SayService service = proxy.getProxy(SayService.class);

        for(int i = 0; i < 10; i ++) {
            String res = service.say("rpc");
            String bye = service.bye();
            System.out.println(bye);
            System.out.println(res);
        }
    }

}
