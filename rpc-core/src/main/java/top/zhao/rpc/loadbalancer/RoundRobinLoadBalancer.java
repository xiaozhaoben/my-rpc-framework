package top.zhao.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 *
 *@author xiaozhao
 */
public class RoundRobinLoadBalancer implements LoadBalancer{

    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        index = index % instances.size();
        return instances.get(index++);
    }
}
