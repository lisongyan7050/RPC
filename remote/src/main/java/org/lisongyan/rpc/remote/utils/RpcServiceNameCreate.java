package org.lisongyan.rpc.remote.utils;

public class RpcServiceNameCreate {

        // key: 服务名 value: 服务提供方s
        public static String buildServiceKey(String serviceName, String serviceVersion) {
            return String.join("$", serviceName, serviceVersion);
        }
}
