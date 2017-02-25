package rpc.netty;
import rpc.core.CallBackContainer;
import rpc.core.MessageCallBack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import rpc.protocol.RpcRequest;

/**
 * Created by kfzx-liuyz1 on 2017/2/21.
 */
public class CliMessageSendHandler extends ChannelOutboundHandlerAdapter {

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(msg, promise);
        RpcRequest request = (RpcRequest)msg;
        CallBackContainer.instance.getMapCallback().put(request.getMessgageId(),new MessageCallBack(request));
        request.send();
    }
}
