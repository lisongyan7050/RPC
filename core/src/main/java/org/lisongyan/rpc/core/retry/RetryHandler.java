package org.lisongyan.rpc.core.retry;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.core.retry.strategy.IRetryStrategy;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.exception.RpcException;
import org.lisongyan.rpc.remote.utils.ResponceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
public class RetryHandler implements IRetryHandler {
    private final int maxAttempts;
    private final long retryDelayMs;
    private final IRetryStrategy strategy;

    public RetryHandler(int maxAttempts, long retryDelayMs, String faultTolerantType) {
        this.maxAttempts = maxAttempts;
        this.retryDelayMs = retryDelayMs;
        this.strategy = RetryStratgyFactory.getRetryStrategy(faultTolerantType);
    }

    @Override
    public RpcResponse executeWithRetry(RpcRequest rpcRequest, ConnectPool connectPool, List<ServiceMeta> serviceMetaList, BiFunction<RpcRequest, ChannelFuture, RpcResponse> doQuery) throws Exception {

        log.info("开始执行重试机制");

        return strategy.handleRetry(rpcRequest, connectPool, serviceMetaList, doQuery,maxAttempts,retryDelayMs);
    }
}
