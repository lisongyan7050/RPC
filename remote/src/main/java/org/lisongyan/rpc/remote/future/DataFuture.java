package org.lisongyan.rpc.remote.future;


import io.netty.util.concurrent.Future;

import java.util.concurrent.CountDownLatch;

public class DataFuture<T> {

    private T data;

    private CountDownLatch count;
    public DataFuture(){
        count = new CountDownLatch(1);
    }


    public T get() throws InterruptedException {
        count.await();
        return this.data;
    }
    public void setSuccess(T data){
        this.data = data;
        count.countDown();
    }
}
