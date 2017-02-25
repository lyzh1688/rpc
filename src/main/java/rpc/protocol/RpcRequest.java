package rpc.protocol;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kfzx-liuyz1 on 2017/2/20.
 */
public class RpcRequest implements Serializable {

    private String messgageId;
    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] paramsVals;
    private Lock sendLock = new ReentrantLock();
    private Condition sendCond = this.sendLock.newCondition();

    public void send() {
        sendLock.lock();
        sendCond.signal();
        sendLock.unlock();
    }

    public String getMessgageIdSync(){
        try {
            this.sendLock.lock();
            this.sendCond.await();
            return this.messgageId;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            this.sendLock.unlock();
        }
    }

    public String getMessgageId() {
        return messgageId;
    }

    public void setMessgageId(String messgageId) {
        this.messgageId = messgageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParamsVals() {
        return paramsVals;
    }

    public void setParamsVals(Object[] paramsVals) {
        this.paramsVals = paramsVals;
    }

}
