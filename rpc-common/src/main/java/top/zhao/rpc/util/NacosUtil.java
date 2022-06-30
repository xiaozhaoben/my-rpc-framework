package top.zhao.rpc.util;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * nacos工具类
 *
 * @author xiaozhao
 */
@Slf4j
public class NacosUtil {

    private static final String SERVER_ADDRESS = "127.0.0.1:8848";

    public static NamingService getNamingService(){
        try {
            return NamingFactory.createNamingService(SERVER_ADDRESS);
        } catch (NacosException e) {
            log.error("连接时发生错误");
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    public static void registerService(NamingService namingService, String serviceName, InetSocketAddress address) throws NacosException {
        namingService.registerInstance(serviceName, address.getHostName(), address.getPort());
    }

    public static List<Instance> getAllInstance(NamingService namingService, String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

}
