package org.lisongyan.rpc.remote;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.domain.RpcRequest;
import org.lisongyan.rpc.remote.utils.HessianUtils;

import java.util.List;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest
    extends TestCase
{
    public void test(){
        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel socketChannel) throws Exception {
                socketChannel.pipeline()
                        .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 0))
                        .addLast(new ByteToMessageDecoder() {

                            @Override
                            protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                                int contentLength = byteBuf.readInt();
                                byte[] bytes = new byte[contentLength];
                                byteBuf.readBytes(bytes);
                                Object o = HessianUtils.parseObject(bytes);
                                list.add(o);
                            }
                        })
                        .addLast(new SimpleChannelInboundHandler<RpcRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
                                log.info("接收到的对象的值是：{},不向后穿低了", rpcRequest.getRequestId());

                            }
                        });
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId();
        byte[] bytes = HessianUtils.toBytes(rpcRequest);
        ByteBuf buffer = embeddedChannel.alloc().buffer();
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        embeddedChannel.writeInbound(buffer);


    }


    public void test2(){

        try {
            System.out.println("你好");
            throw new Exception("栋");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("发生异常了");
        }

    }
}
