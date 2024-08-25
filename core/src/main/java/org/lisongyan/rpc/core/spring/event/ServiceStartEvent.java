package org.lisongyan.rpc.core.spring.event;

import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.cache.RpcSericeMetaCache;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.register.Register;
import org.lisongyan.rpc.core.register.RegisterFactory;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.spring.config.AttributeConfig;
import org.lisongyan.rpc.core.spring.constant.RpcSpringConstant;
import org.lisongyan.rpc.remote.RpcServiceStart;
import org.lisongyan.rpc.remote.utils.ThreadPoolFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class ServiceStartEvent implements ApplicationListener<ContextRefreshedEvent> {


    @Resource
    private AttributeConfig attributeConfig;
    @Resource
    private RpcConfig rpcConfig;


    public ServiceStartEvent(){

    }


    public static void main(String[] args) {
        Integer i1 = 11;
        Integer i = new Integer(11);

        System.out.println(i==i1);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        开启一个单线程启动netty

        ThreadPoolFactory.startNettyPool.execute(new RpcServiceStart(attributeConfig.getAddress(),attributeConfig.getPort()));


        log.info("netty启动！");

        Register register = rpcConfig.getRegister();
        RpcSericeMetaCache rpcSericeMetaCache = rpcConfig.getRpcSericeMetaCache();

        // 发布要发布的内容
        log.info("发布要发布的内容！{}",RpcSpringConstant.serviceMetaList);
        RpcSpringConstant.serviceMetaList.forEach(register::registerInstance);

//        订阅要订阅的内容
        log.info("订阅要订阅的内容！{}",RpcSpringConstant.serviceNameKeyList);

        RpcSpringConstant.serviceNameKeyList.forEach(key->{

            List<ServiceMeta> serviceMetas = register.subscribeInstance(key);
            rpcSericeMetaCache.updateServiceMetaInfo(key,serviceMetas);

        });

    }
}
