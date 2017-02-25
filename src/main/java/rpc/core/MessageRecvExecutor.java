package rpc.core;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by kfzx-liuyz1 on 2017/2/21.
 */
public class MessageRecvExecutor {

    private static final MessageRecvExecutor messageRecvExecutor = new MessageRecvExecutor();

    private ThreadPoolExecutor threadPoolExecutor;

    private MessageRecvExecutor(){
        this.threadPoolExecutor = (ThreadPoolExecutor) RpcThreadPoolExecutor.getExecutor(16,16);
    }

    public static MessageRecvExecutor Instance(){
        return messageRecvExecutor;
    }
    public void submit(Runnable task){
        this.threadPoolExecutor.submit(task);
    }
}
