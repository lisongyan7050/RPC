package org.lisongyan.rpc.remote;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import lombok.extern.slf4j.Slf4j;

import org.lisongyan.rpc.remote.handler.ServiceHandlerInit;

@Slf4j
public class RpcServiceStart implements Runnable{
    private ServerBootstrap bootstrap = null;
    private NioEventLoopGroup boss = null;
    private NioEventLoopGroup worker = null;

    private String address = null;
    private int port = 0;



    public RpcServiceStart(String address, int port){

        assert address != null;
        assert port > 0;

        this.address = address;
        this.port = port;
    }

    public void start() {
        try {
            bootstrap = new ServerBootstrap();
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            bootstrap.group(boss,worker);
            bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.childHandler(new ServiceHandlerInit());
            ChannelFuture channelFuture = bootstrap.bind(address, port).sync();

            if (channelFuture.isSuccess()) {
                log.info("启动成功,端口：{}", port);
            }
            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            destroy();
        }


    }
    protected void destroy() {

        log.info("开始执行优雅关闭");
        // 优雅的关闭 释放资源
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    @Override
    public void run() {
        this.start();
    }

}
