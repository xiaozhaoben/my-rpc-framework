package top.zhao.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;
import top.zhao.rpc.loadbalancer.LoadBalancer;
import top.zhao.rpc.loadbalancer.RandomLoadBalancer;
import top.zhao.rpc.util.NacosUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Nacos服务注册中心
 *
 *@author xiaozhao
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery{

    private LoadBalancer loadBalancer = new RandomLoadBalancer();

    public NacosServiceDiscovery() {
    }

    public NacosServiceDiscovery(LoadBalancer loadBalancer){
        if (loadBalancer != null){
            this.loadBalancer = loadBalancer;
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生:", e);
        }
        return null;
    }
}
