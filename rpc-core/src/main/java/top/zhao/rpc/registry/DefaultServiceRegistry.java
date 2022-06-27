package top.zhao.rpc.registry;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认服务注册表
 *
 *@author xiaozhao
 */
@Slf4j
public class DefaultServiceRegistry implements ServiceRegistry{

    //服务表
    private final static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    //已注册的服务名
    private final static Set<String> registerService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized  <T> void register(T service) {
        String serviceName = service.getClass().getCanonicalName();
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
    public synchronized Object getRegistry(String ServiceName) {
        if (serviceMap.containsKey(ServiceName)){
            return serviceMap.get(ServiceName);
        }else {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
    }
}
