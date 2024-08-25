package org.lisongyan.rpc.core.proxy;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.lisongyan.rpc.core.session.Session;
import org.lisongyan.rpc.remote.utils.RpcServiceNameCreate;
import org.lisongyan.rpc.remote.domain.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class ProxyInvoker<T> implements MethodInterceptor {
    private Session session;
    private final Class<T> interfaceClass;
    private String serviceNameKey;
    private String beanName;
    private String serviceVersion;
    public ProxyInvoker(Session session, Class<T> interfaceClass, String serviceVersion,String beanName){
        this.session = session;
        this.interfaceClass = interfaceClass;
        this.beanName = beanName;
        this.serviceVersion = serviceVersion;
        this.serviceNameKey = RpcServiceNameCreate.buildServiceKey(beanName,serviceVersion);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("开始执行代理方法：{}",method.getName());
//        构造参数
        RpcRequest rpcRequest = getRpcRequest(method, objects);
        Object result = session.exec(rpcRequest, serviceNameKey);
        log.info("执行结束，执行结果是：{}",result);

        return result;
    }

    private RpcRequest getRpcRequest(Method method, Object[] objects) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setInterfaceName(interfaceClass.getName());
        rpcRequest.setBeanName(beanName);
        rpcRequest.setArgs(objects);
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setServiceVersion(serviceVersion);
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setRequestId();
        return rpcRequest;
    }

}
