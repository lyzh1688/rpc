package rpc.netty;

import rpc.core.Const;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class MessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //处理粘包
        channelPipeline.addLast("decoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, Const.MESSAGE_LENGTH, 0, Const.MESSAGE_LENGTH));
        channelPipeline.addLast("encoder", new LengthFieldPrepender(Const.MESSAGE_LENGTH,false));
        //通过基于Java原生对象序列化机制的编码器
        channelPipeline.addLast(new ObjectEncoder());
        channelPipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        channelPipeline.addLast(new CliMessageSendHandler());
        channelPipeline.addLast(new CliMessageRecvHandler());
    }
}
