package rpc.core;

import rpc.protocol.RpcReponse;
import rpc.protocol.RpcRequest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by kfzx-liuyz1 on 2017/2/20.
 */
public class MessageCallBack {

    private RpcRequest rpcRequest;
    private RpcReponse rpcResponse;
    
    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    private static final int TIMEOUT = 10 * 1000;

    public MessageCallBack(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }

    public Object start() throws InterruptedException {
        try {
            this.lock.lock();
            finish.await(TIMEOUT, TimeUnit.MILLISECONDS);
            if(this.rpcResponse != null){
                return this.rpcResponse.getResultObj();
            }
            else {
                return null;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void finish(RpcReponse rpcResponse) {
        //TO DO:超时设置
        try{
            this.lock.lock();
            this.finish.signal();
            this.rpcResponse = rpcResponse;
        }
        finally {
            this.lock.unlock();
        }

    }


}
