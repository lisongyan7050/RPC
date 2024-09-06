package org.lisongyan.rpc.core.annotation;

import org.lisongyan.rpc.core.spring.config.AutoConfig;
import org.lisongyan.rpc.core.spring.processor.ClientComsumer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * 这是一个启动rpc的注解，这个注解需要加在springboot的启动类上（其他地方也行，主要是为了便于查看）
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({AutoConfig.class})
public @interface EnableRpc {
}
