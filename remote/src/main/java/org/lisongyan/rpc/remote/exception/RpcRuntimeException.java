package org.lisongyan.rpc.remote.exception;

public class RpcRuntimeException extends RuntimeException {

    public RpcRuntimeException(String msg){
        super(msg);
    }
    public RpcRuntimeException(Exception e,String msg){
        super(msg,e);
    }
}
