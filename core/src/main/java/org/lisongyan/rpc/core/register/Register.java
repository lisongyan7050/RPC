package org.lisongyan.rpc.core.register;

import org.lisongyan.rpc.core.domain.ServiceMeta;

import java.util.List;
import java.util.Set;

public interface Register {
    /**
     * 注册实例
     */
    void registerInstance(ServiceMeta serviceMeta);

    /**
     * 注销所有实例
     * @param serviceMetaSet
     */
    public void deAllRegisterInstance(Set<ServiceMeta> serviceMetaSet);

    /**
     * 注销单个实例
     * @param serviceMeta
     */
    public void deRegisterInstance(ServiceMeta serviceMeta);



    /**
     * 订阅自己所需要的服务到缓存中
     * @param serviceNameKey
     */
    public List<ServiceMeta> subscribeInstance(String serviceNameKey);
}
