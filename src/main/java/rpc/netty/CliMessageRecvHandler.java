package rpc.netty;

import rpc.core.CallBackContainer;
import rpc.core.MessageCallBack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.protocol.RpcReponse;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class CliMessageRecvHandler extends ChannelInboundHandlerAdapter {

//    private volatile Channel channel;

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcReponse response = (RpcReponse) msg;
        System.out.println("Client receive message");
        String messageId = response.getMessageId();
        MessageCallBack callBack = CallBackContainer.instance.getMapCallback().get(messageId);
        if (callBack != null) {
            CallBackContainer.instance.getMapCallback().remove(messageId);
            callBack.finish(response);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
