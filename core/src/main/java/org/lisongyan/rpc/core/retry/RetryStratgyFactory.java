package org.lisongyan.rpc.core.retry;

import org.lisongyan.rpc.core.retry.strategy.IRetryStrategy;
import org.lisongyan.rpc.core.retry.strategy.impl.FailFastStrategy;
import org.lisongyan.rpc.core.retry.strategy.impl.FailoverStrategy;
import org.lisongyan.rpc.core.retry.strategy.impl.FailsafeStrategy;

public class RetryStratgyFactory {



    public static IRetryStrategy getRetryStrategy(String type)
    {
        if("failfast".equals(type))
        {
            return new FailFastStrategy();
        }
        else if("failover".equals(type))
        {
            return new FailoverStrategy();
        }
        else if("failsafe".equals(type))
        {
            return new FailsafeStrategy();
       }
        throw new RuntimeException("not support retry strategy type:"+type);
    }
}
