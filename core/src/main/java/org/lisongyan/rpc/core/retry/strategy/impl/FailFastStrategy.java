package org.lisongyan.rpc.core.retry.strategy.impl;

import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.core.retry.strategy.IRetryStrategy;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.utils.ResponceCode;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class FailFastStrategy implements IRetryStrategy {
    @Override
    public RpcResponse handleRetry(RpcRequest rpcRequest, ConnectPool connectPool, List<ServiceMeta> serviceMetaList, BiFunction<RpcRequest, ChannelFuture, RpcResponse> doQuery,int maxAttempts, long retryDelayMs) throws Exception {
        // 快速失败策略的实现
        log.warn("rpc 调用失败,触发 FailFast 策略");
        return new RpcResponse(rpcRequest.getRequestId(), ResponceCode.UN_SUCCESS.getCode(), ResponceCode.UN_SUCCESS.getMsg(), null);
    }
}

