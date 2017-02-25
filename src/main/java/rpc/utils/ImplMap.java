package rpc.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by kfzx-liuyz1 on 2017/2/24.
 */
@XStreamAlias("mapping")
public class ImplMap {
    private String intface;
    private String impl;

    public String getIntface() {
        return intface;
    }

    public void setIntface(String intface) {
        this.intface = intface;
    }

    public String getImpl() {
        return impl;
    }

    public void setImpl(String impl) {
        this.impl = impl;
    }
}
