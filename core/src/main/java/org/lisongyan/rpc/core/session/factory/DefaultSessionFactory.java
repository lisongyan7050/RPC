package org.lisongyan.rpc.core.session.factory;

import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.executor.Executor;
import org.lisongyan.rpc.core.session.Session;
import org.lisongyan.rpc.core.session.impl.DefaultSession;

public class DefaultSessionFactory {

    private RpcConfig rpcConfig;

    private static volatile DefaultSessionFactory INSTANCE;
    private DefaultSessionFactory(RpcConfig rpcConfig){
        this.rpcConfig = rpcConfig;
    }
    public static DefaultSessionFactory getInstance(RpcConfig rpcConfig) {
        if (INSTANCE == null) {
            synchronized (DefaultSessionFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DefaultSessionFactory(rpcConfig);
                }
            }
        }
        return INSTANCE;
    }


    public Session OpenSession() {
        Executor executor = rpcConfig.getExecutor();

        return new DefaultSession(executor,rpcConfig);
    }



}
