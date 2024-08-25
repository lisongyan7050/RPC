package org.lisongyan.rpc.core.pool;

import io.netty.channel.ChannelFuture;
import org.lisongyan.rpc.core.domain.ServiceMeta;

public interface ConnectPool {

    ChannelFuture getConnectFuture(ServiceMeta serviceMeta);


    void closeConnectFuture(ChannelFuture channelFuture);
}
