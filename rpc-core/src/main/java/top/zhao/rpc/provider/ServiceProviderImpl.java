package top.zhao.rpc.provider;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认服务注册表，保存服务端本地服务
 *
 *@author xiaozhao
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {

    //服务表
    private final static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    //已注册的服务名
    private final static Set<String> registerService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service, Class<T> serviceClass) {
        String serviceName = serviceClass.getCanonicalName();
        if (registerService.contains(serviceName)){
            throw new RpcException(RpcError.SERVICE_IS_ALREADY_REGISTERED);
        }
        registerService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> anInterface : interfaces) {
            serviceMap.put(anInterface.getCanonicalName(), service);
        }
        log.info("向接口:{} 注册服务：{}",interfaces, serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        if (serviceMap.containsKey(serviceName)){
            return serviceMap.get(serviceName);
        }else {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
    }
}
