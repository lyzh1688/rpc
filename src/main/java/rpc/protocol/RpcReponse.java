package rpc.protocol;

import java.io.Serializable;

/**
 * Created by kfzx-liuyz1 on 2017/2/20.
 */
public class RpcReponse implements Serializable {
    private String messageId;
    private String errorMsg;
    private Object resultObj;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public void setResultObj(Object resultObj) {
        this.resultObj = resultObj;
    }
}
