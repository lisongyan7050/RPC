package org.lisongyan.rpc.core.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcComsumer {

    /**
     * 版本
     * @return
     */
    String serviceVersion() default "1.0";
    /**
     * 重试次数
     * @return
     */
    long retryCount() default 3;

}
