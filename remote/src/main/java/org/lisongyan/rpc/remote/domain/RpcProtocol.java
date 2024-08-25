package org.lisongyan.rpc.remote.domain;

import cn.hutool.core.date.DateUtil;
import org.lisongyan.rpc.remote.utils.HessianUtils;

import java.io.Serializable;
import java.util.Arrays;

public class RpcProtocol implements Serializable {
    private short magic = 0x13;
    // 时间戳
    private int timeStamp;
    // 区分是请求还是响应  0：请求  1：响应
    private short msgType;
    // 消息体长度
    private int contentLength;
    // 消息体
    private byte[] content;
    public RpcProtocol(RpcRequest rpcRequest) {
        this.timeStamp = (int) (DateUtil.current() / 1000);
        this.msgType = (short) 0;
        this.content = HessianUtils.toBytes(rpcRequest);
        this.contentLength = this.content.length;
    }

    public RpcProtocol(RpcResponse rpcResponse) {
        this.timeStamp = (int) (DateUtil.current() / 1000);
        this.msgType = (short) 1;
        this.content = HessianUtils.toBytes(rpcResponse);
        this.contentLength = this.content.length;
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId();
        byte[] jsonBytes = HessianUtils.toBytes(rpcRequest);
        System.out.println(((RpcRequest)HessianUtils.parseObject(jsonBytes)).getRequestId());
    }
    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public short getMsgType() {
        return msgType;
    }

    public void setMsgType(short msgType) {
        this.msgType = msgType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "magic=" + magic +
                ", timeStamp=" + timeStamp +
                ", msgType=" + msgType +
                ", contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
