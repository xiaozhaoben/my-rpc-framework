package top.zhao.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * rpc服务端
 *
 *@author xiaozhao
 */
@Slf4j
class RpcServer {

    private final ExecutorService threadPool;


    RpcServer() {
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void register(Object service, int port){
        try(ServerSocket serverSocket = new ServerSocket();) {
            log.info("服务器启动中");
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                log.info("客户端连接成功!, ip为:{}", socket.getInetAddress());
                threadPool.execute(new WorkThread(service, socket));
            }
        } catch (IOException e) {
            log.error("连接发生错误", e);
        }
    }
}