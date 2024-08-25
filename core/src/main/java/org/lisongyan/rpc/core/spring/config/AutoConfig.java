package org.lisongyan.rpc.core.spring.config;

import org.lisongyan.rpc.core.register.Register;
import org.lisongyan.rpc.core.register.RegisterFactory;
import org.lisongyan.rpc.core.session.config.RpcConfig;
import org.lisongyan.rpc.core.spring.event.ServiceClostEvent;
import org.lisongyan.rpc.core.spring.event.ServiceStartEvent;
import org.lisongyan.rpc.core.spring.processor.ClientComsumer;
import org.lisongyan.rpc.core.spring.processor.ServiceProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(AttributeConfig.class)
public class AutoConfig {

    @Resource
    private AttributeConfig attributeConfig;



    @Bean
    public RpcConfig rpcConfig(){

        return new RpcConfig(attributeConfig);
    }

    @Bean
    public ClientComsumer clientComsumer() {
       return new ClientComsumer();
    }


    @Bean
    public ServiceProvider serviceProvider() {
        return new ServiceProvider();
    }


    @Bean
    public ServiceClostEvent serviceClostEvent(){
        return new ServiceClostEvent();
    }

    @Bean
    public ServiceStartEvent serviceStartEvent(){
        return new ServiceStartEvent();
    }


}
