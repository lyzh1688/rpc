package rpc.netty;

import rpc.protocol.RpcRequest;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public interface Client {
    public void startSendTask(RpcRequest request) throws Exception;

}
