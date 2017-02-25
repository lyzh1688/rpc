package rpc.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kfzx-liuyz1 on 2017/2/22.
 */
public class CallBackContainer {

    public static final CallBackContainer instance = new CallBackContainer();


    private ConcurrentHashMap<String, MessageCallBack> mapCallback = new ConcurrentHashMap<String, MessageCallBack>();

    private CallBackContainer(){}

    public CallBackContainer Instance(){
        return instance;
    }

    public ConcurrentHashMap<String, MessageCallBack> getMapCallback() {
        return mapCallback;
    }
}
