package org.lisongyan.rpc.core.spring.processor;

import org.lisongyan.rpc.core.annotation.RpcComsumer;
import org.lisongyan.rpc.core.proxy.ProxyBeanFactory;
import org.lisongyan.rpc.core.register.Register;
import org.lisongyan.rpc.core.register.RegisterFactory;
import org.lisongyan.rpc.core.session.Session;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.session.factory.DefaultSessionFactory;
import org.lisongyan.rpc.core.session.impl.DefaultSession;
import org.lisongyan.rpc.core.spring.constant.RpcSpringConstant;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;
import org.lisongyan.rpc.remote.utils.RpcServiceNameCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Modifier;

public class ClientComsumer implements InstantiationAwareBeanPostProcessor {
    @Resource
    private  RpcConfig rpcConfig;

    private  Session session;




    private static final Logger log = LoggerFactory.getLogger(ClientComsumer.class);

    public ClientComsumer(){

    }


    @PostConstruct
    public void init(){
        this.session = DefaultSessionFactory.getInstance(rpcConfig).OpenSession();

    }
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        ReflectionUtils.doWithLocalFields(aClass,field -> {
            if (field.isAnnotationPresent(RpcComsumer.class)) {
                if (Modifier.isStatic(field.getModifiers())) {
                    throw new IllegalStateException("静态属性不能使用这个注解");
                }
                RpcComsumer annotation = field.getAnnotation(RpcComsumer.class);
                String name = field.getType().getName();
                String serviceVersion = annotation.serviceVersion();
                Object proxyBean = ProxyBeanFactory.getClientProxyBean(field.getType(), this.session, serviceVersion, name);

                try {
                    field.setAccessible(true);
                    field.set(bean,proxyBean);
                }catch (Exception e){
                    throw new RpcRuntimeException("设置bean属性出错");
                }
                //添加 待订阅的信息
                String key = RpcServiceNameCreate.buildServiceKey(name, serviceVersion);
                RpcSpringConstant.serviceNameKeyList.add(key);
            }
        });


        return pvs;
    }
}
