package top.zhao.transport;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RPC客户端动态代理
 *
 *@author xiaozhao
 */
@Slf4j
@AllArgsConstructor
public class RpcClientProxy implements InvocationHandler {

    private String host;
    private int port;

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest()
                .setInterfaceName(method.getDeclaringClass().getName())
                .setMethodName(method.getName())
                .setParameters(args)
                .setParamTypes(method.getParameterTypes());
        log.info("调用方法：{}", request.getMethodName());
        RpcClient rpcClient = new RpcClient();
        return ((RpcResponse) rpcClient.sendRequest(request, host, port)).getData();
    }
}
