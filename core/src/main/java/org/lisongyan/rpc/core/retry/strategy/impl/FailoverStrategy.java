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
@Slf4j
public class FailoverStrategy implements IRetryStrategy {




    @Override
    public RpcResponse handleRetry(RpcRequest rpcRequest, ConnectPool connectPool, List<ServiceMeta> serviceMetaList, BiFunction<RpcRequest, ChannelFuture, RpcResponse> doQuery,int maxAttempts, long retryDelayMs) throws Exception {

        int attempt = 0;

        while (attempt < maxAttempts) {
            log.info("开始重试第 {} 次", attempt);

            // 故障转移策略的实现
            if (!ObjectUtils.isEmpty(serviceMetaList)) {
                final ServiceMeta serviceMeta = serviceMetaList.iterator().next();

                serviceMetaList.remove(serviceMeta);

                try {
                    return doQuery.apply(rpcRequest, connectPool.getConnectFuture(serviceMeta));
                }catch (Exception e){
                    attempt++;
                    log.info("休息 {} 毫秒", retryDelayMs);
                    Thread.sleep(retryDelayMs);
                }

            } else {
                log.info("rpc 调用失败,无服务可用");
                throw new RuntimeException("rpc 调用失败,无服务可用");
            }



        }
        throw new Exception("重试次数已达到上限");


    }
}

