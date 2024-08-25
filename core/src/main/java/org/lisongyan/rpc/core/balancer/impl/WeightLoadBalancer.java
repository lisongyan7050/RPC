package org.lisongyan.rpc.core.balancer.impl;

import org.lisongyan.rpc.core.balancer.AbstractLoadBalancer;
import org.lisongyan.rpc.core.balancer.ServiceMetaRes;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.remote.exception.RpcException;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class WeightLoadBalancer extends AbstractLoadBalancer  {



    private Random random = new Random();

    // LSY TODO 2024/8/4 暂时没有去解决 ，某个节点掉线或者某个节点挂掉，需要重新计算权重
    private Map<String,Integer[]> weightCache = new ConcurrentHashMap();
    /**
     * 构造函数，初始化负载均衡器。
     * @param rpcConfig RPC配置信息，用于获取服务元数据缓存。
     */
    public WeightLoadBalancer(RpcConfig rpcConfig) {
        super(rpcConfig);
    }


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

        if (serviceInstanceList.size() == 1) {
            // 如果服务实例列表只有一个实例，直接返回该实例
            return ServiceMetaRes.build(serviceInstanceList.get(0), serviceInstanceList);
        }

        Integer[] preSum = weightCache.computeIfAbsent(serviceNameKey, v -> initPreSum(serviceInstanceList));


        Integer max = preSum[preSum.length - 1];
        int rand = random.nextInt(max) + 1;
        for (int i = 0; i < preSum.length; i++) {
            if (rand <= preSum[i]) {
                return ServiceMetaRes.build(serviceInstanceList.get(i), serviceInstanceList);
            }
        }



        return null;
    }

    private Integer[] initPreSum(List<ServiceMeta> serviceInstanceList){
        Integer[] preSum= new Integer[serviceInstanceList.size()];

        preSum  = new Integer[serviceInstanceList.size()];
        preSum[0] = serviceInstanceList.get(0).getWeight();
        for (int i = 1; i < serviceInstanceList.size(); i++) {
            preSum[i] = preSum[i-1] + serviceInstanceList.get(i).getWeight();
        }
        return preSum;

    }
}
