package top.zhao.rpc.registry;

/**
 * 服务注册接口
 *
 *@author xiaozhao
 */
public interface ServiceRegistry {

    /**
     * 注册服务
     * @param service 待注册服务实体
     * @param <T> 服务实体类
     */
    <T> void register(T service);

    /**
     * 获取注册的服务
     * @param ServiceName 服务名
     * @return 服务实体类
     */
    Object getRegistry(String ServiceName);

}
