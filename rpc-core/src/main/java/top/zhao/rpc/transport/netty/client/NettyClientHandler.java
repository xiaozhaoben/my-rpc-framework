package top.zhao.rpc.transport.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import top.zhao.rpc.entity.RpcResponse;

/**
 * Netty中客户端的Handler
 *
 *@author xiaozhao
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
        try {
            log.info(String.format("客户端接收到消息: %s", response));
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + response.getRequestId());
            channelHandlerContext.channel().attr(key).set(response);
            channelHandlerContext.channel().close();
        } finally {
            ReferenceCountUtil.release(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("过程调用时有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }

}
