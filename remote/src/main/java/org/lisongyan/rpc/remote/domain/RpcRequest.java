package org.lisongyan.rpc.remote.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.FutureTask;

public class RpcRequest implements Serializable {
    private String requestId;

    private String serviceVersion;

    private String beanName;

    private String interfaceName;
    /**
     * 需要调用的方法的入参属性
     */
    /**
     * 需要调用的方法
     */
    private String methodName;  //方法
    private Class[] paramTypes; //入参属性
    /**
     * 需要调用的方法的入参
     */
    private Object[] args;      //入参



    public RpcRequest(){

    }

    public RpcRequest(Class[] paramTypes, Object[] args) {
        this.requestId = UUID.randomUUID().toString();
        this.paramTypes = paramTypes;
        this.args = args;

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId() {
        this.requestId = UUID.randomUUID().toString();;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "requestId='" + requestId + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setInterfaceName(String className) {
        this.interfaceName = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
