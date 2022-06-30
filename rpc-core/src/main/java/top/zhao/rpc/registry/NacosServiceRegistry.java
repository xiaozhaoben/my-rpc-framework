package top.zhao.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;
import top.zhao.rpc.util.NacosUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Nacos服务注册中心
 *
 *@author xiaozhao
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry{


    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
