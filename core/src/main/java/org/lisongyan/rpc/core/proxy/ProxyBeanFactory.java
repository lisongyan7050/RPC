package org.lisongyan.rpc.core.proxy;

import net.sf.cglib.proxy.Enhancer;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.session.Session;

import java.util.Comparator;

public class ProxyBeanFactory {

    public static <T>T getClientProxyBean(Class<T> calzz, Session session,String serviceVersion,String beanName){
        return (T) Enhancer.create(calzz,new ProxyInvoker<T>(session,calzz,serviceVersion,beanName));
    }
}
