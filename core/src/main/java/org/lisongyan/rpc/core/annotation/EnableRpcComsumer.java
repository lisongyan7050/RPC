package org.lisongyan.rpc.core.annotation;


import org.lisongyan.rpc.core.spring.processor.ClientComsumer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ClientComsumer.class})
public @interface EnableRpcComsumer {
}
