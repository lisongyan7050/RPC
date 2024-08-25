package org.lisongyan.rpc.core.balancer;

import org.lisongyan.rpc.core.balancer.impl.RoundRobinLoadBalancer;
import org.lisongyan.rpc.core.balancer.impl.WeightLoadBalancer;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;

public class LoadBalancerFactory {

    public static LoadBalancer getLoadBalancer(String type, RpcConfig rpcConfig) {

        switch (type){
            case "Polling": return new RoundRobinLoadBalancer(rpcConfig);
            case "Weight": return new WeightLoadBalancer(rpcConfig);
            default: throw new RpcRuntimeException("没有对应的负载均衡");
        }

    }
}
