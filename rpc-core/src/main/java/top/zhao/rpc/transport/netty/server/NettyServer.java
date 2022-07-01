package top.zhao.rpc.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.codec.CommonDecoder;
import top.zhao.rpc.codec.CommonEncoder;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;
import top.zhao.rpc.hook.ShutdownHook;
import top.zhao.rpc.provider.ServiceProvider;
import top.zhao.rpc.provider.ServiceProviderImpl;
import top.zhao.rpc.registry.NacosServiceRegistry;
import top.zhao.rpc.registry.ServiceRegistry;
import top.zhao.rpc.serializer.CommonSerializer;
import top.zhao.rpc.serializer.JsonSerializer;
import top.zhao.rpc.serializer.KryoSerializer;
import top.zhao.rpc.transport.AbstractRpcServer;
import top.zhao.rpc.transport.RpcServer;

import java.net.InetSocketAddress;

import static top.zhao.rpc.serializer.CommonSerializer.DEFAULT_SERIALIZER;

/**
 * netty服务端
 *
 *@author xiaozhao
 */
@Slf4j
@Data
public class NettyServer extends AbstractRpcServer {

    private CommonSerializer serializer;

    public NettyServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }
    public NettyServer(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanServices();
    }
    @Override
    public void start() {
        if(serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new CommonEncoder(serializer));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(host, port).sync();
            ShutdownHook.getShutdownHook().addClearAllHook();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            log.error("启动服务器时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
