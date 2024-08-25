package org.lisongyan.rpc.remote.cache;

import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.future.DataFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcRemoteContext {
    private static Map<String, DataFuture<RpcResponse>> CLIENT_REQUEST_CACHE = new ConcurrentHashMap<>(1024);


    /**
     * 生产者Bean缓存 beanName:bean
     */
    private static Map<String, Object> PRODUCER_BEAN_CACHE = new ConcurrentHashMap<>(32);

    public static void addDataFutureCache(String requestId,DataFuture<RpcResponse> dataFuture){
        CLIENT_REQUEST_CACHE.put(requestId,dataFuture);
    }

    public static DataFuture<RpcResponse> getDataFuture(String requestId){
        return CLIENT_REQUEST_CACHE.remove(requestId);
    }

    public static void putBeanToCache(String key,Object obj){
        PRODUCER_BEAN_CACHE.put(key,obj);
    }

    public static Object getBeanFromCache(String key){
        Object result = PRODUCER_BEAN_CACHE.get(key);
        return result;
    }
}
