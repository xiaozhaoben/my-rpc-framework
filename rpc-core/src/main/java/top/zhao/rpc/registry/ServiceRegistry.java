package top.zhao.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
 *
 *@author xiaozhao
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 获取注册的服务
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

}
