package rpc.core;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import rpc.protocol.RpcReponse;
import rpc.protocol.RpcRequest;
import rpc.utils.ReflectUtils;

/**
 * Created by kfzx-liuyz1 on 2017/2/21.
 */
public class MessageRecvTask implements Runnable {

    private RpcRequest rpcRequest = null;
    private RpcReponse rpcReponse = null;
    private ChannelHandlerContext context = null;

    public MessageRecvTask(RpcRequest request, RpcReponse reponse, ChannelHandlerContext context){
        this.context = context;
        this.rpcRequest = request;
        this.rpcReponse = reponse;
    }

    public void run() {
        this.rpcReponse.setMessageId(this.rpcRequest.getMessgageId());
        try{
            Object responseObj = ReflectUtils.invoke(this.rpcRequest);
            this.rpcReponse.setResultObj(responseObj);
            this.context.writeAndFlush(this.rpcReponse).addListener(new ChannelFutureListener(){
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    System.out.println("RPC Server Send message-id respone:" + rpcRequest.getMessgageId());
                }
            });
        }
        catch (Exception e){
            this.rpcReponse.setErrorMsg(e.getMessage());
            e.printStackTrace();
            System.err.printf("RPC Server invoke error!\n");
        }
    }
}
