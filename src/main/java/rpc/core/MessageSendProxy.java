package rpc.core;

import rpc.protocol.RpcRequest;
import rpc.netty.NettyClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kfzx-liuyz1 on 2017/2/20.
 */
public class MessageSendProxy<T> implements InvocationHandler {

    Class<T> clazz;
    NettyClient nettyClient = null;
//    private Lock lock = new ReentrantLock();
//    Condition condition = lock.newCondition();

    public MessageSendProxy(Class<T> clazz,NettyClient nettyClient){
        this.clazz = clazz;
        this.nettyClient = nettyClient;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setMessgageId(UUID.randomUUID().toString());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamTypes(method.getParameterTypes());
        rpcRequest.setParamsVals(args);
        //TO DO: send the request
        this.nettyClient.startSendTask(rpcRequest);
//        while (true){
//            if(CallBackContainer.instance.getMapCallback().containsKey(rpcRequest.getMessgageId())){
//                break;
//            }
//        }

        String messageId = rpcRequest.getMessgageIdSync();
        return CallBackContainer.instance.getMapCallback().get(messageId).start() ;

    }
}
