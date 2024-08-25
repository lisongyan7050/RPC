package org.lisongyan.rpc.core.annotation;


import org.lisongyan.rpc.core.spring.event.ServiceClostEvent;
import org.lisongyan.rpc.core.spring.event.ServiceStartEvent;
import org.lisongyan.rpc.core.spring.processor.ServiceProvider;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ServiceProvider.class, ServiceStartEvent.class, ServiceClostEvent.class})
public @interface EnableRpcProvider {
}
