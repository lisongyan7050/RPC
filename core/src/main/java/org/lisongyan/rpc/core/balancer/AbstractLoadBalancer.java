package org.lisongyan.rpc.core.balancer;

import org.lisongyan.rpc.core.session.config.RpcConfig;

public abstract class AbstractLoadBalancer implements LoadBalancer{

    protected final RpcConfig rpcConfig;
    public AbstractLoadBalancer(RpcConfig rpcConfig) {
        this.rpcConfig = rpcConfig;
    }

}
