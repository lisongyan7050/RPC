package org.lisongyan.rpc.remote.domain;

import org.lisongyan.rpc.remote.utils.ResponceCode;

import java.io.Serializable;

public class RpcResponse implements Serializable {
    private String requestId;
    private int code;
    private String msg;
    private Object data;
    private String exceptionMsg;

    public RpcResponse(){

    }

    public RpcResponse(String requestId, int code, String msg, Object data, String exception) {
        this.requestId = requestId;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.exceptionMsg = exception;
    }

    public RpcResponse(String requestId, int code, String msg, Object data) {
        this.requestId = requestId;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public void setResponseCode(ResponceCode responceCode){
        this.code = responceCode.getCode();
        this.msg = responceCode.getMsg();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getException() {
        return exceptionMsg;
    }

    public void setException(String exception) {
        this.exceptionMsg = exception;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", exception='" + exceptionMsg + '\'' +
                '}';
    }
}
