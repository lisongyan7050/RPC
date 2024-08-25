package org.lisongyan.rpc.core.register;

import org.lisongyan.rpc.core.register.impl.RedisRegister;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;

public class RegisterFactory {

    private static Register register;

    public static Register getRegister(String type){
        if (register!=null){
            return register;
        }
        switch (type){
            case "redis":
                register = new RedisRegister();
                return register;
            default:
                throw new RpcRuntimeException("其他类型的 注册中心还没实现");


        }
    }
}
