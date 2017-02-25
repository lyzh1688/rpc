package rpc.netty;

import rpc.core.MessageRecvExecutor;
import rpc.core.MessageRecvTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.protocol.RpcReponse;
import rpc.protocol.RpcRequest;

/**
 * Created by kfzx-liuyz1 on 2017/2/21.
 */
public class SrvMessageRecvHandler extends ChannelInboundHandlerAdapter {

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server receive request");
        RpcRequest request = (RpcRequest) msg;
        RpcReponse response = new RpcReponse();
        MessageRecvTask recvTask = new MessageRecvTask(request, response, ctx);
        //不要阻塞nio线程，复杂的业务逻辑丢给专门的线程池
        MessageRecvExecutor.Instance().submit(recvTask);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //网络有异常要关闭通道
        ctx.close();
    }
}
