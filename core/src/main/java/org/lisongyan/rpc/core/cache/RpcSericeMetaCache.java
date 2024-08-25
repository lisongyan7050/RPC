package org.lisongyan.rpc.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.register.Register;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class RpcSericeMetaCache {

    private Register register;

    /** 本地实例缓存  服务名:实例列表 */

    private  Map<String, List<ServiceMeta>> serviceMetaCache=new ConcurrentHashMap<>(16);

    public RpcSericeMetaCache(Register register) {

        this.register=register;
    }


    public  void updateServiceMetaInfo(String serviceNameKey,List<ServiceMeta> serviceMetaList){
        serviceMetaCache.put(serviceNameKey,serviceMetaList);
    }



    public  List<ServiceMeta> getServiceInstanceList(String serviceNameKey){

        List<ServiceMeta> serviceMetas = serviceMetaCache.get(serviceNameKey);

        if (serviceMetas==null|| serviceMetas.isEmpty()){

            log.info("本地订阅的{}已经没了,现在需要重新拉取",serviceNameKey);
            // 重新拉取
            serviceMetas = this.register.subscribeInstance(serviceNameKey);
            serviceMetaCache.put(serviceNameKey,serviceMetas);
            return serviceMetas;
        }

        return serviceMetaCache.get(serviceNameKey);
    }
}
