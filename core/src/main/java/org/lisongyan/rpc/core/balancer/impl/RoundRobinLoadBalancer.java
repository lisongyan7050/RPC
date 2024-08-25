/**
 * 基于轮询算法的负载均衡器。
 * 该类继承自AbstractLoadBalancer，实现了服务选择的轮询策略。
 */
package org.lisongyan.rpc.core.balancer.impl;

import org.lisongyan.rpc.core.balancer.AbstractLoadBalancer;
import org.lisongyan.rpc.core.balancer.ServiceMetaRes;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.remote.exception.RpcException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer extends AbstractLoadBalancer {

    // 使用原子整数来实现线程安全的自增，用于轮询算法
    private static AtomicInteger roundRobinId = new AtomicInteger(0);

    /**
     * 构造函数，初始化负载均衡器。
     * @param rpcConfig RPC配置信息，用于获取服务元数据缓存。
     */
    public RoundRobinLoadBalancer(RpcConfig rpcConfig) {
        super(rpcConfig);
    }



    /**
     * 根据轮询算法选择一个服务实例。
     * @param serviceNameKey 服务名称的键。
     * @return 选中的服务元数据及所有可用的服务列表。
     * @throws RpcException 如果没有找到对应的服务实例，抛出RPC异常。
     */
    @Override
    public ServiceMetaRes select(String serviceNameKey) throws RpcException {

        // 从RPC配置中获取服务元数据缓存
        RpcSericeMetaCache rpcSericeMetaCache = rpcConfig.getRpcSericeMetaCache();
        // 根据服务名称的键获取服务实例列表
        List<ServiceMeta> serviceInstanceList = rpcSericeMetaCache.getServiceInstanceList(serviceNameKey);

        // 如果服务实例列表为空，抛出RPC异常
        if (serviceInstanceList == null || serviceInstanceList.isEmpty()) {
            throw new RpcException("没有对应的服务");
        }

        // 获取服务实例列表的大小
        // 1.获取所有服务
        int size = serviceInstanceList.size();
        // 自增轮询ID，并根据列表大小取模，以实现轮询选择
        int index = roundRobinId.addAndGet(1);

        // 当轮询ID超过一定值时，重置为0，防止溢出
        // 避免 出现溢出
        if (index>=1000000){
            roundRobinId.set(0);
        }
        // 根据轮询ID选择服务实例，并返回所有实例的列表
        return ServiceMetaRes.build(serviceInstanceList.get(index%size),serviceInstanceList);
    }
}
