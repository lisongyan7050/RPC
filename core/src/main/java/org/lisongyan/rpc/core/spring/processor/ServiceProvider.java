package org.lisongyan.rpc.core.spring.processor;

import org.lisongyan.rpc.core.annotation.RpcProvider;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.spring.config.AttributeConfig;
import org.lisongyan.rpc.core.spring.constant.RpcSpringConstant;
import org.lisongyan.rpc.remote.cache.RpcRemoteContext;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import javax.annotation.Resource;

/**
 * 服务提供者处理器，实现BeanPostProcessor接口，用于处理标记有RpcProvider注解的bean。
 * 该处理器会在bean初始化后对其进行处理，将符合条件的bean注册为RPC服务。
 */
public class ServiceProvider implements InstantiationAwareBeanPostProcessor {

    // 引入属性配置，用于获取服务的地址和端口信息。
    @Resource
    private AttributeConfig attributeConfig;

    // 日志记录器，用于记录处理过程中的信息。
    private static final Logger log = LoggerFactory.getLogger(ServiceProvider.class);

    /**
     * 在bean初始化后进行处理。
     * 如果bean类上有RpcProvider注解，则将其注册为RPC服务。
     *
     * @param bean 刚被初始化的bean对象。
     * @param beanName bean的名称。
     * @return 经过处理后的bean对象。
     * @throws BeansException 如果处理过程中出现异常。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 检查bean类上是否有RpcProvider注解。
        RpcProvider annotation = bean.getClass().getAnnotation(RpcProvider.class);
        if (annotation == null) {
            // 如果没有RpcProvider注解，则直接返回bean。
            return bean;
        }

        String serviceName;
        try {
            // 通过bean实现的接口名称来确定服务名称。
            serviceName = bean.getClass().getInterfaces()[0].getName();
        } catch (Exception e) {
            // 如果bean没有实现接口，则抛出异常。
            throw new RpcRuntimeException("provider需要实现接口");
        }

        // 如果RpcProvider注解中指定了服务接口，则使用注解中的服务接口名称。
        if (!annotation.serviceInterface().equals(void.class)) {
            serviceName = annotation.serviceInterface().getName();
        }

        // 创建服务元数据，包含服务名称、版本、地址和端口信息。
        // LSY TODO 2024/7/26 服务组需要 在配置里设置
        ServiceMeta serviceMeta = new ServiceMeta("lisongyan", serviceName, annotation.serviceVersion(), attributeConfig.getAddress(), attributeConfig.getPort(),5);

        // 将bean对象缓存起来，以便后续的RPC调用。
//       有注解那么保存当前bean到本地缓存bean中
        RpcRemoteContext.putBeanToCache(serviceMeta.getServiceName(), bean);

        // 将服务元数据添加到待发布的服务列表中。
//       构造servicemeta对象，并且存入待发布的缓存中
        RpcSpringConstant.serviceMetaList.add(serviceMeta);

        // 返回处理后的bean对象。
        return bean;
    }
}
