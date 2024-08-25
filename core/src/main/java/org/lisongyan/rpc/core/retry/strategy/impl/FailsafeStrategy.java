package org.lisongyan.rpc.core.retry.strategy.impl;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.core.retry.strategy.IRetryStrategy;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.utils.ResponceCode;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.function.BiFunction;
public class FailsafeStrategy implements IRetryStrategy {
    @Override
    public RpcResponse handleRetry(RpcRequest rpcRequest, ConnectPool connectPool, List<ServiceMeta> serviceMetaList, BiFunction<RpcRequest, ChannelFuture, RpcResponse> doQuery,int maxAttempts, long retryDelayMs) throws Exception {
        // 忽略错误策略的实现
        return null;
    }
}
