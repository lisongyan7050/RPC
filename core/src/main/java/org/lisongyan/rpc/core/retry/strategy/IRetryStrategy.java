package org.lisongyan.rpc.core.retry.strategy;

import io.netty.channel.ChannelFuture;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;

import java.util.List;
import java.util.function.BiFunction;

public interface IRetryStrategy {
    RpcResponse handleRetry(RpcRequest rpcRequest, ConnectPool connectPool, List<ServiceMeta> serviceMetaList, BiFunction<RpcRequest, ChannelFuture, RpcResponse> doQuery,int maxAttempts, long retryDelayMs) throws Exception;
}
