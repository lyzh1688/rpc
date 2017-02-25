package rpc.core;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by kfzx-liuyz1 on 2017/2/21.
 */
public class RpcThreadPoolExecutor {
    private static String name = "RPC-ThreadPool";

    public static Executor getExecutor(int threads, int queues){
        return new java.util.concurrent.ThreadPoolExecutor(threads
                ,threads
                ,0
                , TimeUnit.MILLISECONDS,(queues < 0 ? new LinkedBlockingQueue<Runnable>(): new LinkedBlockingQueue<Runnable>(queues))
                ,new NamedThreadFactory(name));
    }
}
