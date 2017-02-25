package rpc.core;

import rpc.netty.NettyClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class ClientContainer {

    private static Map<String,NettyClient> clientMap = new HashMap<String,NettyClient>();
    private static final ClientContainer clientContainer = new ClientContainer();

    public static ClientContainer Instance(){
        return clientContainer;
    }

    public NettyClient getClient(String name){
        return clientMap.get(name);
    }

    public void setClient(String clientName,String serverAddr,int port){
        clientMap.put(clientName , new NettyClient(serverAddr,port));
    }
}
