package org.lisongyan.rpc.remote.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.cache.RpcRemoteContext;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.future.DataFuture;

@Slf4j
public class ClientBusinessHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        log.info("收到了响应体 {}",rpcResponse);
            DataFuture<RpcResponse> dataFuture = RpcRemoteContext.getDataFuture(rpcResponse.getRequestId());
            if (dataFuture!=null){
                dataFuture.setSuccess(rpcResponse);
            }

    }
}
