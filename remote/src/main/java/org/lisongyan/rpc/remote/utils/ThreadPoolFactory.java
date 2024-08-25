package org.lisongyan.rpc.remote.utils;

import java.util.concurrent.*;

public class ThreadPoolFactory {

    public static Executor startNettyPool = new ThreadPoolExecutor(1, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-netty-remote");
                    return thread;
                }
            });
    public static ExecutorService fu = new ThreadPoolExecutor(4, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("Easy-Rpc-netty-remote");
                    return thread;
                }
            });



}
