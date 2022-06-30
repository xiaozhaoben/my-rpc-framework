package top.zhao.rpc.hook;

import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.factory.ThreadPoolFactory;
import top.zhao.rpc.util.NacosUtil;

import java.util.concurrent.ExecutorService;

/**
 * 关闭钩子
 *
 * @author xiaozhao
 */
@Slf4j
public class ShutdownHook {

    private final ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("shutdown-hook");
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook() {
        return shutdownHook;
    }

    public void addClearAllHook() {
        log.info("关闭后将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearService();
            threadPool.shutdown();
        }));
    }

}
