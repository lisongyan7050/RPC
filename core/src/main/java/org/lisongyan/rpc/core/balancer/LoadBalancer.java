package org.lisongyan.rpc.core.balancer;

import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.remote.exception.RpcException;

public interface LoadBalancer {


    ServiceMetaRes select(String serviceNameKey) throws RpcException;
}
