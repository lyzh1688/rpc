package rpc.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import rpc.protocol.RpcRequest;
import rpc.netty.MessageSendChannelInitializer;

import java.net.InetSocketAddress;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class MessageSendTask implements Runnable {

    private EventLoopGroup eventLoopGroup = null;
    private String serverAddress;
    private int serverPort;
    private Bootstrap bootstrap = new Bootstrap();
    private RpcRequest request = null;

    public MessageSendTask(String serverAddress, int serverPort, EventLoopGroup eventLoopGroup, RpcRequest request){
        this.eventLoopGroup = eventLoopGroup;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.request = request;
    }

    public void run() {
        try{
            this.bootstrap.group(this.eventLoopGroup);
            this.bootstrap.channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
            this.bootstrap.handler(new MessageSendChannelInitializer());
            final InetSocketAddress serverAddress = new InetSocketAddress(this.serverAddress, this.serverPort);
            ChannelFuture channelFuture = this.bootstrap.connect(serverAddress);
            channelFuture.addListener(new ChannelFutureListener() {
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        //执行几次？每次事件完成后都执行？
                        System.out.println("Request ready send");
                        channelFuture.channel().pipeline().channel().writeAndFlush(request);
                        System.out.println("Request send");
                    }
                }
            });

        }
        catch (Exception e){
            System.out.println("Netty Client MessageSendTask : " + e.getMessage());
            this.eventLoopGroup.shutdownGracefully();
        }
    }
}
