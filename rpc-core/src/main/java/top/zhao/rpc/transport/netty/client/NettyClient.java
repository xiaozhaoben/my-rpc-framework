package top.zhao.rpc.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcRequest;
import top.zhao.rpc.entity.RpcResponse;
import top.zhao.rpc.enums.RpcError;
import top.zhao.rpc.exception.RpcException;
import top.zhao.rpc.loadbalancer.LoadBalancer;
import top.zhao.rpc.registry.NacosServiceDiscovery;
import top.zhao.rpc.registry.NacosServiceRegistry;
import top.zhao.rpc.registry.ServiceDiscovery;
import top.zhao.rpc.registry.ServiceRegistry;
import top.zhao.rpc.serializer.CommonSerializer;
import top.zhao.rpc.transport.RpcClient;
import top.zhao.rpc.util.RpcMessageChecker;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * netty客户端
 *
 *@author xiaozhao
 */
@Slf4j
@Data
public class NettyClient implements RpcClient {

    private static final Bootstrap bootstrap;

    private CommonSerializer serializer;
    private final ServiceDiscovery serviceDiscovery;
    private LoadBalancer loadBalancer;

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }


    public NettyClient() {
        serviceDiscovery = new NacosServiceDiscovery();
    }

    public NettyClient(int serializerId, LoadBalancer loadBalancer){
        this.serviceDiscovery = new NacosServiceDiscovery(loadBalancer);
        this.serializer = CommonSerializer.getByCode(serializerId);
    }


    @Override
    public Object sendRequest(RpcRequest request) {
        if(serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            Channel channel = ChannelProvider.get(serviceDiscovery.lookupService(request.getInterfaceName()), serializer);
            if(channel.isActive()) {
                channel.writeAndFlush(request).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        log.info(String.format("客户端发送消息: %s", request.toString()));
                    } else {
                        log.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + request.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(request, rpcResponse);
                result.set(rpcResponse.getData());
            } else {
                System.exit(0);
            }
        } catch (InterruptedException e) {
            log.error("发送消息时有错误发生: ", e);
        }
        return result.get();
    }
}
