package top.zhao.rpc.transport.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;
import top.zhao.rpc.factory.SingletonFactory;
import top.zhao.rpc.factory.ThreadPoolFactory;
import top.zhao.rpc.provider.ServiceProvider;
import top.zhao.rpc.provider.ServiceProviderImpl;
import top.zhao.rpc.transport.socket.server.RequestHandler;

import java.util.concurrent.ExecutorService;

/**
 * Netty中处理RpcRequest的Handler
 *
 *@author xiaozhao
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static RequestHandler requestHandler;
    private static ServiceProvider serviceProvider;
    private final ExecutorService threadPool;
    private static final String THREAD_NAME_PREFIX = "netty-server-handler";

    public NettyServerHandler() {
        this.requestHandler = SingletonFactory.getInstance(RequestHandler.class);
        this.serviceProvider = new ServiceProviderImpl();
        this.threadPool = ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        threadPool.execute(() -> {
            try {
                log.info("服务器接收到请求: {}", request);
                String interfaceName = request.getInterfaceName();
                Object service = serviceProvider.getServiceProvider(interfaceName);
                Object result = requestHandler.handle(request, service);
                ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, request.getRequestId()));
                future.addListener(ChannelFutureListener.CLOSE);
            } finally {
                ReferenceCountUtil.release(request);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理过程中发生错误:");
        cause.printStackTrace();
        ctx.close();
    }
}
