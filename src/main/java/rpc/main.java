package rpc;

import rpc.core.ClientContainer;
import rpc.core.MessageSendExecutor;
import rpc.netty.NettyServer;
import rpc.test.Test;
import rpc.test.User;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class main {
    public static void main(String[] args) throws Exception {
        ClientContainer.Instance().setClient("local","127.0.0.1",9090);
        new Thread(new Runnable() {
            public void run() {
                NettyServer nettyServer = new NettyServer();
                try {
                    nettyServer.start(9090);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000);

        MessageSendExecutor executor = new MessageSendExecutor("local");

        for(int i = 0 ; i < 100; ++i){
            new Thread(new Runnable() {
                public void run() {
                    Test test = executor.execute(Test.class);
                    User user = test.testObj("Yuezhi","Liu");
                    System.out.println(user.toString());
                }
            }).start();
        }



    }
}
