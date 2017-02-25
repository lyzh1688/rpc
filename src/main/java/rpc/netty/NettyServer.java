package rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import rpc.core.Const;
import rpc.core.NamedThreadFactory;
import rpc.utils.ReflectUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kfzx-liuyz1 on 2017/2/20.
 */
public class NettyServer implements Server {

    private ServerBootstrap bootstrap = null;
    private AtomicBoolean startFlag = new AtomicBoolean(false);
    //private static final int NPROCESSORS = Runtime.getRuntime().availableProcessors() * 2;

    public NettyServer(){
        this.init();
    }

    public void init(){
        ThreadFactory serverLeaderThreadFactory = new NamedThreadFactory("RPC-SERVER-LEADER-");
        ThreadFactory serverWorkerThreadFactory = new NamedThreadFactory("RPC-SERVER-WORKER-");
        EventLoopGroup leaderGroup = new NioEventLoopGroup(Const.NPROCESSORS,serverLeaderThreadFactory);
        EventLoopGroup workerGroup = new NioEventLoopGroup(Const.NPROCESSORS * 2,serverWorkerThreadFactory);
        try{
            ReflectUtils.getImplMappings();
            this.bootstrap = new ServerBootstrap();
            bootstrap.group(leaderGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .option(ChannelOption.SO_BACKLOG, 128);
        }
        catch (Exception e){
            System.out.println("NettyServer occurs error : ");
            e.printStackTrace();
            leaderGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        finally{
        }

    }

    public void start(int listenPort) throws Exception {
        if(!this.startFlag.compareAndSet(false,true)){
            return;
        }
        this.bootstrap.childHandler(new MessageRecvChannelInitializer());
        System.out.printf("Netty RPC Server startSendTask success port:%d\n", listenPort);
        bootstrap.bind(new InetSocketAddress(listenPort)).sync();
    }

}
