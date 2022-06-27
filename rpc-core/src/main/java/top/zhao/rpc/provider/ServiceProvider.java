package top.zhao.rpc.provider;

/**
 * 保存和提供服务实例对象
 *
 * @author xiaozhao
 */
public interface ServiceProvider {

    /**
     * 增加服务
     * @param service
     * @param <T>
     */
    <T> void addServiceProvider(T service);

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    Object getServiceProvider(String serviceName);

}
