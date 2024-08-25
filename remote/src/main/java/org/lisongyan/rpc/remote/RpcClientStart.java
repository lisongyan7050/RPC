package org.lisongyan.rpc.remote;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.exception.RpcRuntimeException;
import org.lisongyan.rpc.remote.handler.ClientHandlerInit;

@Slf4j
public class RpcClientStart implements Runnable{
    private Bootstrap bootstrap = null;
    private ChannelFuture channelFuture;
    private NioEventLoopGroup worker ;

    private String address;
    private Integer port;

    public RpcClientStart(String address,Integer port){
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ClientHandlerInit());
        this.address = address;
        this.port = port;
    }
    public ChannelFuture get()  {
        try {
            channelFuture = bootstrap.remoteAddress(this.address, this.port).connect().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        channelFuture.addListener(event -> {
            if (event.isSuccess()) {
                log.info("连接Rpc Server 成功,地址：{},端口：{}", this.address, this.port);
            } else {
                throw new RpcRuntimeException(String.format("Rpc connect server:[ %s:%d ] fail",this.address, this.port));
            }
        });


        return channelFuture;
    }

    public void destroy() {
        // 优雅的关闭 释放资源
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        try {
            log.info("开始监听 channel的关闭");

            channelFuture.channel().closeFuture().sync();

            log.error("channel关闭");
        } catch (InterruptedException e) {
            log.error("Easy-Rpc Client start error：{},{}", e.getMessage(), e);
            throw new RpcRuntimeException(e.getMessage());
        }finally {
            destroy();
        }
    }

}
