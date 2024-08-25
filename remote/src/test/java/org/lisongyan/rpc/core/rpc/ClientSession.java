package org.lisongyan.rpc.core.rpc;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class ClientSession {
    public static final AttributeKey<ClientSession> SESSION_KEY =
            AttributeKey.valueOf("SESSION_KEY");

    private Channel channel;

    private String sessionId; //保存登录后的服务端sessionid
    private Boolean isConnected = false;
    private Boolean isLogin = false;

    //绑定通道
    public ClientSession(Channel channel) {
        this.channel = channel;
        this.sessionId = String.valueOf(-1);
        //重要：ClientSession绑定到Channel
        channel.attr(ClientSession.SESSION_KEY).set(this);
    }
}
//登录成功之后，设置sessionId
