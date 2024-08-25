package org.lisongyan.rpc.core.pool;

import org.lisongyan.rpc.core.pool.Impl.DefaultConnectPool;

public class ConnectPoolFactory {


    public static ConnectPool getConnectPool(String type) {
        switch (type) {
            case "default": return new DefaultConnectPool();
            default: return new DefaultConnectPool();
        }
    }
}
