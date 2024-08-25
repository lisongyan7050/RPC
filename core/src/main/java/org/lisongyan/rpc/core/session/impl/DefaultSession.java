package org.lisongyan.rpc.core.session.impl;

import lombok.Data;
import org.lisongyan.rpc.core.executor.Executor;
import org.lisongyan.rpc.core.session.Session;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.remote.domain.RpcRequest;

/**
 * 默认的会话实现类，提供会话管理的基本功能。
 * 会话是客户端和服务器之间通信的抽象，用于管理请求的执行和配置信息。
 */
@Data
public class DefaultSession implements Session {
    /**
     * 执行器，用于执行RPC请求。
     * 执行器根据配置信息和请求参数，实际负责向远程服务发送请求并获取响应。
     */
    private Executor executor;
    /**
     * RPC配置信息，包含会话相关的配置参数。
     * 如服务的地址、端口、通信协议等，用于指导执行器如何进行RPC调用。
     */
    private RpcConfig rpcConfig;

    /**
     * 构造函数，初始化会话对象。
     * @param executor 执行器，负责执行RPC请求。
     * @param rpcConfig RPC配置信息，用于配置会话的行为。
     */
    public DefaultSession(Executor executor, RpcConfig rpcConfig){
        this.executor = executor;
        this.rpcConfig = rpcConfig;
    }

    /**
     * 执行RPC请求。
     * 根据提供的RPC请求和服务名称键，通过执行器向远程服务发送请求，并返回请求的结果数据。
     * @param rpcRequest RPC请求对象，包含请求的具体参数。
     * @param serviceNameKey 服务名称键，用于标识要调用的服务。
     * @return 返回执行RPC请求后得到的数据对象。
     */
    @Override
    public Object exec(RpcRequest rpcRequest, String serviceNameKey) {
        // 通过执行器执行RPC请求，获取响应对象
        Object data = executor.query(rpcRequest, serviceNameKey).getData();
        // 返回响应数据
        return data;
    }

}
