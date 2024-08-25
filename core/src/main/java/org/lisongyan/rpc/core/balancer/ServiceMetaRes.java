package org.lisongyan.rpc.core.balancer;


import org.lisongyan.rpc.core.domain.ServiceMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceMetaRes {

    // 当前服务节点
    private ServiceMeta curServiceMeta;

    // 剩余服务节点
    private List<ServiceMeta> otherServiceMeta;

    public List<ServiceMeta> getOtherServiceMeta() {
        return otherServiceMeta;
    }

    public ServiceMeta getCurServiceMeta() {
        return curServiceMeta;
    }

    public static ServiceMetaRes build(ServiceMeta curServiceMeta, List<ServiceMeta> otherServiceMeta){
        ServiceMetaRes serviceMetaRes = new ServiceMetaRes();
        serviceMetaRes.curServiceMeta = curServiceMeta;
        // 如果只有一个服务
        if(otherServiceMeta.size() == 1){
            otherServiceMeta = null;
        }else{
            otherServiceMeta.remove(curServiceMeta);
        }
        serviceMetaRes.otherServiceMeta = otherServiceMeta;
        return serviceMetaRes;
    }

}
