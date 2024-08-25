package org.lisongyan.rpc.remote.handler.service;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.cache.RpcRemoteContext;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.domain.RpcResponse;
import org.lisongyan.rpc.remote.domain.RpcProtocol;
import org.lisongyan.rpc.remote.utils.ResponceCode;
import org.lisongyan.rpc.remote.utils.RpcServiceNameCreate;

import java.lang.reflect.Method;

@Slf4j
public class ServiceBusinessHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        log.info("接收到的对象的請求id是：{}", rpcRequest.getRequestId());

        log.info("当前执行的线程是：{}", Thread.currentThread().getName());
        RpcResponse rpcResponse = new RpcResponse();
        Object invoke = null;
        try {
            String serviceNameKey = RpcServiceNameCreate.buildServiceKey(rpcRequest.getBeanName(), rpcRequest.getServiceVersion());


            log.info("serviceNameKey:{}", serviceNameKey);
            log.info("rpcRequest.getBeanName():{}", rpcRequest.getBeanName());

            Object bean = RpcRemoteContext.getBeanFromCache(serviceNameKey);


            Class<?> aClass = Class.forName(rpcRequest.getInterfaceName());

            Method method = aClass.getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());

            invoke = method.invoke(bean, rpcRequest.getArgs());

        } catch (Exception e) {
            e.printStackTrace();
            rpcResponse.setResponseCode(ResponceCode.UN_SUCCESS);
            rpcResponse.setException("服务端发生异常");
        }



        rpcResponse.setData(invoke);
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        rpcResponse.setResponseCode(ResponceCode.SUCCESS);
        RpcProtocol rpcProtocol = new RpcProtocol(rpcResponse);
        log.info("写入数据{}", rpcProtocol);
        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(rpcProtocol);
        channelFuture.addListener(gc -> {
            if (gc.isDone()) {
                log.error("消息发送成功");
            }
        });

    }
}
