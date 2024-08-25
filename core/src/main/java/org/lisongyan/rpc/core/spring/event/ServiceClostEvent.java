package org.lisongyan.rpc.core.spring.event;

import org.lisongyan.rpc.core.register.Register;
import org.lisongyan.rpc.core.register.RegisterFactory;
import org.lisongyan.rpc.core.spring.constant.RpcSpringConstant;
import org.lisongyan.rpc.remote.utils.RpcServiceNameCreate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class ServiceClostEvent implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        Register register = RegisterFactory.getRegister("redis");

        // 取消注册
        register.deAllRegisterInstance(RpcSpringConstant.serviceMetaList);


    }
}
