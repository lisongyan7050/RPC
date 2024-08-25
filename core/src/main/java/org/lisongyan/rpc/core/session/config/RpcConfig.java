package org.lisongyan.rpc.core.session.config;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.executor.Executor;
import org.lisongyan.rpc.core.executor.impl.DefaultExecutor;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.core.pool.ConnectPoolFactory;
import org.lisongyan.rpc.core.register.Register;
import org.lisongyan.rpc.core.register.RegisterFactory;
import org.lisongyan.rpc.core.spring.config.AttributeConfig;
import org.lisongyan.rpc.remote.RpcClientStart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Data
@Slf4j
public class RpcConfig {
    private Executor executor;

    private Register register;
    private RpcSericeMetaCache rpcSericeMetaCache;

    private AttributeConfig attributeConfig;

    private ConnectPool connectPool;

    public RpcConfig(AttributeConfig attributeConfig) {
        this.attributeConfig = attributeConfig;
        this.register = RegisterFactory.getRegister(attributeConfig.getRegisterType()==null? "redis" : attributeConfig.getRegisterType());
        this.rpcSericeMetaCache = new RpcSericeMetaCache(this.register);
        this.connectPool = ConnectPoolFactory.getConnectPool(attributeConfig.getConnectPoolType());

    }

    public Executor getExecutor(){
        if (executor==null){
            return new DefaultExecutor(this);
        }
        return executor;
    }

    public RpcConfig(){

    }


    @Deprecated
    public ChannelFuture getChannelFuture(ServiceMeta serviceMeta){

      return   connectPool.getConnectFuture(serviceMeta);

    }




}
