package org.lisongyan.rpc.remote.exception;

public class RpcException extends Exception{
    public RpcException(String msg){
        super(msg);
    }
    public RpcException(Exception e,String msg){
        super(msg,e);
    }
}
