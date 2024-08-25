/**
 * 会话接口。
 * 该接口定义了如何执行RPC请求的方法，是RPC框架中会话管理的核心接口。
 * 它负责接收远程调用请求，并根据服务名称键执行相应的操作。
 */
package org.lisongyan.rpc.core.session;

import org.lisongyan.rpc.remote.domain.RpcRequest;

public interface Session {

    /**
     * 执行RPC请求。
     *
     * @param rpcRequest RPC请求对象，包含调用的服务方法名和参数等信息。
     * @param serviceNameKey 服务名称的键，用于查找和定位具体的服务实现。
     * @return 执行结果，通常是调用服务方法后的返回值。
     */
    public Object exec(RpcRequest rpcRequest,String serviceNameKey);

}
