package org.lisongyan.rpc.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.lisongyan.rpc.remote.utils.RpcServiceNameCreate;

@Data
@AllArgsConstructor
@ToString
public class ServiceMeta {
    private String serviceGroup;
    private String serviceName;
    private String serviceVersion;

    private String address;
    private Integer port;


    // LSY TODO 2024/8/3 实现权重
    private Integer weight;
    private String endTime;

    public ServiceMeta(String serviceGroup, String serviceName, String serviceVersion, String address, Integer port, Integer weight) {
        this.serviceGroup = serviceGroup;
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        this.address = address;
        this.port = port;
        this.weight = weight;
        buildServiceKey();
    }





    public void buildServiceKey() {
        this.serviceName = RpcServiceNameCreate.buildServiceKey(serviceName, serviceVersion);
    }

}
