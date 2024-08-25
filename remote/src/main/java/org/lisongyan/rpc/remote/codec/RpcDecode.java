package org.lisongyan.rpc.remote.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.remote.utils.HessianUtils;

import java.util.List;

@Slf4j
public class RpcDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        short magic = byteBuf.readShort();
        int TimeStamp = byteBuf.readInt();
        short MsgType = byteBuf.readShort();
        int contentLength = byteBuf.readInt();
        byte[] bytes = new byte[contentLength];


        log.info("读取的数据magic：{},TimeStamp: {},MsgType: {},contentLength: {}",magic,TimeStamp,MsgType,contentLength);
        byteBuf.readBytes(bytes);
        Object o = HessianUtils.parseObject(bytes);
        list.add(o);
    }
}
