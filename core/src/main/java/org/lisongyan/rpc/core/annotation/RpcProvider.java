package org.lisongyan.rpc.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcProvider {
    /**
     * 指定实现方,默认为实现接口中第一个
     * @return
     */
    Class<?> serviceInterface() default void.class;

    /**
     * 版本
     * @return
     */
    String serviceVersion() default "1.0";
}
