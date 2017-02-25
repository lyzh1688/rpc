package rpc.core;

import rpc.netty.NettyClient;

import java.lang.reflect.Proxy;

/**
 * Created by kfzx-liuyz1 on 2017/2/20.
 */
public class MessageSendExecutor {

    private NettyClient nettyClient = null;

    public MessageSendExecutor(String clientName){
        this.nettyClient = ClientContainer.Instance().getClient(clientName);
    }

    public <T> T execute(Class<T> rpcInterface){
        return (T) Proxy.newProxyInstance(rpcInterface.getClassLoader(),new Class<?>[] {rpcInterface},new MessageSendProxy<T>(rpcInterface,this.nettyClient));
    }
}
