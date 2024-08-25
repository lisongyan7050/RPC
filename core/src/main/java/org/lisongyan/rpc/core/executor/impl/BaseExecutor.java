package org.lisongyan.rpc.core.executor.impl;

import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.balancer.LoadBalancer;
import org.lisongyan.rpc.core.balancer.LoadBalancerFactory;
import org.lisongyan.rpc.core.balancer.ServiceMetaRes;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.core.retry.IRetryHandler;
import org.lisongyan.rpc.core.retry.RetryHandler;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.executor.Executor;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.exception.RpcException;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;
import org.lisongyan.rpc.remote.utils.ResponceCode;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.ServiceLoader;

/**
 * 执行器的基类，实现了执行器接口。
 * 提供了负载均衡、故障容错处理等基础功能。
 */
@Slf4j
public abstract class BaseExecutor implements Executor {
    // RPC配置信息
    protected RpcConfig rpcConfig;
    // 负载均衡器
    private LoadBalancer balancer;
    // 故障容错类型
    private String faultTolerantType;

    private final ConnectPool connectPool;

    private IRetryHandler retryHandler;
    /**
     * 构造函数，初始化负载均衡器和故障容错类型。
     * @param rpcConfig RPC配置信息
     */
    public BaseExecutor(RpcConfig rpcConfig){
        this.rpcConfig = rpcConfig;
        this.faultTolerantType   = rpcConfig.getAttributeConfig().getFaultTolerantType();
        balancer = LoadBalancerFactory.getLoadBalancer("Polling", rpcConfig);
        connectPool = rpcConfig.getConnectPool();
        retryHandler = new RetryHandler(5,1000,faultTolerantType);
    }


    /**
     * 查询并执行RPC调用。
     * 使用负载均衡策略选择服务提供者，尝试调用服务，遇到异常时根据故障容错策略处理。
     * @param rpcRequest RPC请求
     * @param serviceNameKey 服务名称键
     * @return RPC响应
     */
    @Override
    public RpcResponse query(RpcRequest rpcRequest,String serviceNameKey) {
        ServiceMetaRes serviceMetaRes = null;
        try {
            // 使用负载均衡器选择服务提供者
            // 负载均衡

            synchronized (serviceNameKey){
                serviceMetaRes = balancer.select(serviceNameKey);

            }

        } catch (RpcException e) {
            // 处理负载均衡选择失败的情况
            e.printStackTrace();
            return new RpcResponse(rpcRequest.getRequestId(), ResponceCode.UN_SUCCESS.getCode(), e.getMessage(), null, "没有对应的服务了");
        }


        RpcResponse rpcResponse = null;
        long count = 0;

        ServiceMeta curServiceMeta = serviceMetaRes.getCurServiceMeta();
        List<ServiceMeta> otherServiceMeta = serviceMetaRes.getOtherServiceMeta();

        try {
            ChannelFuture channel = connectPool.getConnectFuture(curServiceMeta);
            // 执行具体的RPC查询操作
            rpcResponse = doQuery(rpcRequest, channel);

        }catch (Exception e){
            try {
                return retryHandler.executeWithRetry(rpcRequest, connectPool, otherServiceMeta, this::doQuery);
            } catch (Exception ex) {
                return new RpcResponse(rpcRequest.getRequestId(), ResponceCode.UN_SUCCESS.getCode(), ResponceCode.UN_SUCCESS.getMsg(), null, ex.getMessage());
            }
        }
        return rpcResponse;
    }



    /**
     * 执行具体的RPC查询操作。
     * @param rpcRequest RPC请求
     * @param channelFuture 通道未来对象
     * @return RPC响应
     */
    protected abstract RpcResponse doQuery(RpcRequest rpcRequest, ChannelFuture channelFuture) ;
}
