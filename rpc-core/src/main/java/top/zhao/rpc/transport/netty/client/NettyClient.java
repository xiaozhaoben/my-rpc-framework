package top.zhao.rpc.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.codec.CommonDecoder;
import top.zhao.rpc.codec.CommonEncoder;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;
import top.zhao.rpc.serializer.JsonSerializer;
import top.zhao.rpc.transport.RpcClient;

/**
 * netty客户端
 *
 *@author xiaozhao
 */
@Slf4j
public class NettyClient implements RpcClient {

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest request) {
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            log.info("客户端连接服务器{}:{}",host,port);
            Channel channel = future.channel();
            if(channel != null){
                channel.writeAndFlush(request).addListener(future1 -> {
                    if (future1.isSuccess()){
                        log.info("客户端发送消息: {}", request.toString());
                    }else {
                        log.error("发送消息出现错误:",future.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                log.info("获取数据:{}", rpcResponse.getData());
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            log.error("发生消息时发生错误:", e);
        }
        return null;
    }
}
