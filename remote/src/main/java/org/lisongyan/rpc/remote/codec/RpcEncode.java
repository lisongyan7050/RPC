package org.lisongyan.rpc.remote.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.lisongyan.rpc.remote.domain.RpcProtocol;

public class RpcEncode extends MessageToByteEncoder<RpcProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol rpcProtocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeShort(rpcProtocol.getMagic());// 写入魔法值
        byteBuf.writeInt(rpcProtocol.getTimeStamp());//写入时间戳
        byteBuf.writeShort(rpcProtocol.getMsgType());//写入消息类型 0 请求，1 响应
        byteBuf.writeInt(rpcProtocol.getContentLength());// 写入消息长度
        byteBuf.writeBytes(rpcProtocol.getContent());//写入消息内容
    }
}
