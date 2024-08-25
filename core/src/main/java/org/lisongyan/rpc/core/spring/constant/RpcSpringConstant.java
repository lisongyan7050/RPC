package org.lisongyan.rpc.core.spring.constant;

import cn.hutool.core.collection.ConcurrentHashSet;
import org.lisongyan.rpc.core.domain.ServiceMeta;

import java.util.Map;
import java.util.Set;

public class RpcSpringConstant {
    /** 需要订阅的服务列表 */
    public static Set<String> serviceNameKeyList=new ConcurrentHashSet<>(16);

    /** 需要发布的服务元数据 */
    public static Set<ServiceMeta> serviceMetaList=new ConcurrentHashSet<>(16);
}
