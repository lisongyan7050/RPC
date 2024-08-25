package org.lisongyan.rpc.core.spring.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("lean-rpc")
public class AttributeConfig {

    private int port;
    private String address;
    private String registerType;
    private String loadBalancerType = "balancer";

    private String faultTolerantType = "failover";



    private String connectPoolType = "default";


    public String getConnectPoolType() {
        return connectPoolType;
    }

    public void setConnectPoolType(String connectPoolType) {
        this.connectPoolType = connectPoolType;
    }

    public String getFaultTolerantType() {
        return faultTolerantType;
    }

    public void setFaultTolerantType(String faultTolerantType) {
        this.faultTolerantType = faultTolerantType;
    }



    public String getLoadBalancerType() {
        return loadBalancerType;
    }

    public void setLoadBalancerType(String loadBalancerType) {
        this.loadBalancerType = loadBalancerType;
    }



    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }
}
