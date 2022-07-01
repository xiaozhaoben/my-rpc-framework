package top.zhao.rpc.transport;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.annotation.Service;
import top.zhao.rpc.annotation.ServiceScan;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;
import top.zhao.rpc.provider.ServiceProvider;
import top.zhao.rpc.registry.ServiceRegistry;
import top.zhao.rpc.serializer.CommonSerializer;
import top.zhao.rpc.util.ReflectUtil;

import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.util.Set;

@Slf4j
public abstract class AbstractRpcServer implements RpcServer{

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(ServiceScan.class)){
                log.error("启动类确实ServiceScan注解");
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            log.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }
        //基础包
        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        //默认包为启动类所在包
        if("".equals(basePackage)) {
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }
        //获取包下所有类
        Set<Class<?>> classes = ReflectUtil.getClasses(basePackage);
        for (Class<?> clazz : classes) {
            //将包含Service注解的类注册
            if (clazz.isAnnotationPresent(Service.class)){
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object o;
                try {
                    o = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("创建{}类发生错误", clazz);
                    continue;
                }
                if ("".equals(serviceName)){
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        publishService(o, anInterface.getCanonicalName());
                    }
                }else {
                    publishService(o, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }
}
