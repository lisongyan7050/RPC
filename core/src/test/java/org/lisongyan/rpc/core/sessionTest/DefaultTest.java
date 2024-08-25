package org.lisongyan.rpc.core.sessionTest;

import org.junit.Test;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.lisongyan.rpc.core.register.Register;
import org.lisongyan.rpc.core.register.RegisterFactory;
import org.lisongyan.rpc.core.register.impl.RedisRegister;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.proxy.ProxyBeanFactory;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.core.session.Session;
import org.lisongyan.rpc.core.session.factory.DefaultSessionFactory;

import java.util.Arrays;
import java.util.List;

public class DefaultTest {
//    @Test
//    public void test1(){
//
//
//        RpcRequest rpcRequest = new RpcRequest();
//        rpcRequest.setInterfaceName("org.lisongyan.rpc.remote.testInterface.Weiweiwei");
//        rpcRequest.setBeanName("WeiweiweImpl");
//        rpcRequest.setArgs(new Object[]{"你好"});
//        rpcRequest.setParamTypes(new Class[]{String.class});
//        rpcRequest.setServiceVersion("1.1.3");
//        rpcRequest.setMethodName("hello");
//        rpcRequest.setRequestId();
//
//        Register register = RegisterFactory.getRegister("redis");
//
//        RpcConfig rpcConfig = new RpcConfig(register);
//
//        RpcSericeMetaCache rpcSericeMetaCache = rpcConfig.getRpcSericeMetaCache();
//        ServiceMeta serviceMeta = new ServiceMeta("lisongyan", "test", "1.0", "127.0.0.1", 19988);
//        rpcSericeMetaCache.updateServiceMetaInfo("lisongyan", Arrays.asList(serviceMeta));
//        DefaultSessionFactory sessionFactory = DefaultSessionFactory.getInstance(rpcConfig);
//            Session session = sessionFactory.OpenSession();
//            Object exec = session.exec(rpcRequest,"test$1.0");
//            System.out.println(exec);
//
//    }
//
//    @Test
//    public void test2() throws InterruptedException {
//        Register register = RegisterFactory.getRegister("redis");
//
//        RpcConfig rpcConfig = new RpcConfig(register);
//
//        RpcSericeMetaCache rpcSericeMetaCache = rpcConfig.getRpcSericeMetaCache();
//        ServiceMeta serviceMeta = new ServiceMeta("lisongyan", "weiweiwei", "1.0", "127.0.0.1", 19988);
//        rpcSericeMetaCache.updateServiceMetaInfo(serviceMeta.getServiceName(), Arrays.asList(serviceMeta));
//
//        Session session = DefaultSessionFactory.getInstance(rpcConfig).OpenSession();
//
//        Weiweiwei proxyBean = ProxyBeanFactory.getClientProxyBean(Weiweiwei.class, session, serviceMeta.getServiceVersion(), "weiweiwei");
//        proxyBean.hello("你好");
//        proxyBean.hello("你好");
//        proxyBean.hello("你好");
//        proxyBean.hello("你好");
//        proxyBean.hello("你好");
//        proxyBean.hello("你好");
//    }
//
//    @Test
//    public void test3() throws InterruptedException {
//        Register redisRegister = new RedisRegister();
//        ServiceMeta serviceMeta = new ServiceMeta("lisongyan", "weiweiwei", "1.0", "127.0.0.1", 19988);
//        redisRegister.registerInstance(serviceMeta);
//        redisRegister.subscribeInstance(serviceMeta.getServiceName());
//        RpcConfig rpcConfig = new RpcConfig();
//        Session session = DefaultSessionFactory.getInstance(rpcConfig).OpenSession();
//
//        Weiweiwei proxyBean = ProxyBeanFactory.getClientProxyBean(Weiweiwei.class, session, serviceMeta.getServiceVersion(), "weiweiwei");
//        proxyBean.hello("你好");
//    }
}
