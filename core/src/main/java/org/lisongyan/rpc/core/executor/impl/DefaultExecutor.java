package org.lisongyan.rpc.core.executor.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.cache.RpcRemoteContext;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.remote.domain.RpcProtocol;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;
import org.lisongyan.rpc.remote.future.DataFuture;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DefaultExecutor extends BaseExecutor {
    public DefaultExecutor(RpcConfig rpcConfig) {
        super(rpcConfig);
    }

    @Override
    public RpcResponse doQuery(RpcRequest rpcRequest, ChannelFuture channelFuture) {
        Channel channel = channelFuture.channel();

        DataFuture<RpcResponse> dataFuture = new DataFuture<>();

        RpcRemoteContext.addDataFutureCache(rpcRequest.getRequestId(),dataFuture);

        channel.writeAndFlush(new RpcProtocol(rpcRequest));
        RpcResponse rpcResponse = null;

        try {
            rpcResponse = dataFuture.get();
            String exception = rpcResponse.getException();
                // 如果响应中包含异常信息，关闭连接并抛出异常
            if(exception!=null){
                log.error("rpcResponse error:{}",exception);
                log.info("出现异常了:{}",exception);

                if (!channel.isActive()){
                    rpcConfig.getConnectPool().closeConnectFuture(channelFuture);
                }

                throw new RpcRuntimeException("远程调用出现异常");
            }
        } catch (InterruptedException e) {
            throw new RpcRuntimeException(e,"远程调用出现异常");
        }

        return rpcResponse;
    }


}

