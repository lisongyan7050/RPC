package org.lisongyan.rpc.remote.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.codec.RpcDecode;
import org.lisongyan.rpc.remote.codec.RpcEncode;
import org.lisongyan.rpc.remote.handler.client.ClientBusinessHandler;

@Slf4j
public class ClientHandlerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(1024,8,4,0,0))
                .addLast(new RpcEncode())
                .addLast(new RpcDecode())
                .addLast(new ClientBusinessHandler());
    }
}
