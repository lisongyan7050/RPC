package org.lisongyan.rpc.core.pool.Impl;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.lisongyan.rpc.core.domain.ServiceMeta;
import org.lisongyan.rpc.core.pool.ConnectPool;
import org.lisongyan.rpc.remote.RpcClientStart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认连接池实现类，用于管理与服务提供者的连接。
 */
@Slf4j
public class DefaultConnectPool implements ConnectPool {

    /**
     * 存储每个服务组的连接未来对象列表，以服务组为键，列表中的每个元素代表一个连接尝试。
     */
    private Map<String, CopyOnWriteArrayList<ChannelFuture>> channelFutureCache = new ConcurrentHashMap<>(4);
    /**
     * 存储每个服务组的连接计数器，用于循环使用连接列表中的位置。
     */
    private Map<String, AtomicInteger> atomicIntegerMap = new ConcurrentHashMap<>(4);

    private final Map<String, Object> serviceGroupLocks = new ConcurrentHashMap<>();


    /**
     * 根据服务元数据获取相应的连接未来对象。
     * @param serviceMeta 服务元数据，包含服务组、地址和端口信息。
     * @return 相应服务组的连接未来对象。
     */
    @Override
    public ChannelFuture getConnectFuture(ServiceMeta serviceMeta) {
        String serviceGroup = serviceMeta.getServiceGroup();

        synchronized (getLockForServiceGroup(serviceGroup)) {
            // 根据服务组获取连接列表，如果不存在则初始化为包含4个null元素的列表。
            // 在根据key获取时如果不存在那么初始化大小为4，并且返回
            List<ChannelFuture> channelFutures = channelFutureCache.computeIfAbsent(serviceGroup, key->new CopyOnWriteArrayList<>(Collections.nCopies(4, null)));
            // 根据服务组获取连接计数器，如果不存在则初始化为0。
            AtomicInteger atomicInteger = atomicIntegerMap.computeIfAbsent(serviceGroup, key -> new AtomicInteger(0));

            // 更新连接计数器并根据计数器的值计算当前应使用的连接索引。
            // 当这个组下面有连接时
            int count = atomicInteger.addAndGet(1);
            if (count >= 1000000) {
                // 当计数器达到一定值时重置为0，避免溢出。
                atomicInteger.set(0);
            }
            int index = count % 4;
            ChannelFuture channelFuture = channelFutures.get(index);

            // 如果当前连接对象存在且通道处于激活状态，则直接返回该对象。
            if (channelFuture != null && channelFuture.channel().isActive()) {
                log.info("当前channel可用{}", channelFuture);
                return channelFuture;
            }

            // 如果当前连接未来对象不存在或通道不活跃，则创建新的连接并更新连接列表。
            if (channelFuture == null || !channelFuture.channel().isActive()) {
                log.info("当前channel为null或者 不活跃 需要重新获取链接{}", channelFuture);

                if (!channelFuture.channel().isActive()){
                    channelFuture.channel().close();
                }


                ChannelFuture connectFuture = getConnectFuture(serviceGroup, serviceMeta.getAddress(), serviceMeta.getPort());

                channelFutures.set(index, connectFuture);
                return connectFuture;
            }

            // 如果上述条件都不满足，则直接返回当前连接未来对象。
            return channelFuture;
        }
    }

    private Object getLockForServiceGroup(String serviceGroup) {
        return serviceGroupLocks.computeIfAbsent(serviceGroup, k -> new Object());
    }

    /**
     * 关闭给定的连接未来对象。
     * @param channelFuture 需要关闭的连接未来对象。
     */
    @Override
    public void closeConnectFuture(ChannelFuture channelFuture) {

        try {
            // 异步关闭通道，并同步等待关闭操作完成。
            channelFuture.channel().close().sync();
            log.info("关闭了他");
        } catch (InterruptedException e) {
            // 如果关闭操作被中断，则抛出运行时异常。
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据服务组、地址和端口创建并返回一个新的连接未来对象。
     * @param serviceGroup 服务组。
     * @param address 服务地址。
     * @param port 服务端口。
     * @return 新创建的连接未来对象。
     */
    private ChannelFuture getConnectFuture(String serviceGroup,String address,Integer port) {
        RpcClientStart rpcClientStart = new RpcClientStart(address, port);

        ChannelFuture channelFuture = rpcClientStart.get();
        // 为通道添加关闭监听器，以便在通道关闭时从连接列表中移除对应的连接未来对象。
        // 添加关闭监听
        channelFuture.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("Channel 已关闭,他的serviceGroup是{},future:{}",serviceGroup,channelFuture);
                // 根据服务组找到连接列表，然后移除已关闭的连接未来对象。
                int index = channelFutureCache.get(serviceGroup).indexOf(channelFuture);
                channelFutureCache.get(serviceGroup).set(index,null);
            }
        });
        return channelFuture;
    }

}
