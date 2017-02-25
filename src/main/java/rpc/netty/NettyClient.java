package rpc.netty;

import rpc.core.Const;
import rpc.core.MessageSendTask;
import rpc.core.RpcThreadPoolExecutor;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import rpc.protocol.RpcRequest;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class NettyClient implements Client {

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(Const.NPROCESSORS);
    private static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) RpcThreadPoolExecutor.getExecutor(16, -1);

    private String serverAddress;
    private int serverPort;

    public NettyClient(String serverAddress,int serverPort){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startSendTask(RpcRequest request) throws Exception {
        MessageSendTask messageSendTask = new MessageSendTask(this.serverAddress,this.serverPort,this.eventLoopGroup,request);
        threadPoolExecutor.submit(messageSendTask);
    }


}
