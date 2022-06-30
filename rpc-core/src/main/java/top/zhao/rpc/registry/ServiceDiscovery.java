package top.zhao.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 *
 *@author xiaozhao
 */
public interface ServiceDiscovery {

    /**
     * 获取注册的服务
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

}
