package org.lisongyan.rpc.core.annotation;

import org.lisongyan.rpc.core.spring.config.AutoConfig;
import org.lisongyan.rpc.core.spring.processor.ClientComsumer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({AutoConfig.class})
public @interface EnableRpc {
}
