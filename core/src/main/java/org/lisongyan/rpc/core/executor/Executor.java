package org.lisongyan.rpc.core.executor;

import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;

public interface Executor {
    RpcResponse query(RpcRequest rpcRequest, String serviceNameKey) ;
}
