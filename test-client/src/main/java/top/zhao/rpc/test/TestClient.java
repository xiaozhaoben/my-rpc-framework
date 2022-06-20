package top.zhao.rpc.test;

import top.zhao.rpc.client.RpcClientProxy;
import top.zhao.rpc.api.HelloService;
import top.zhao.rpc.api.HelloEntity;

/**
 *  测试用客户端
 *
 * @author xiaozhao
 */
public class TestClient {

    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloEntity helloEntity = new HelloEntity(1, "爱你");
        String res = helloService.hello(helloEntity);
        System.out.println(res);
    }

}
