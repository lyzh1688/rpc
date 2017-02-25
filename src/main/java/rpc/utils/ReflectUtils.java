package rpc.utils;

import rpc.protocol.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

/**
 * Created by kfzx-liuyz1 on 2017/2/21.
 */
public class ReflectUtils {

    private static Map<String,ImplMap> implMap = null;

    public static void getImplMappings(){
        try {
            URL url = ReflectUtils.class.getClassLoader().getResource("mapping.xml");
            ImplMapHolder implMapHolder = XmlFileUtil.toBeanFromFile("",url.getFile(),ImplMapHolder.class);
            implMap = implMapHolder.toMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invoke(RpcRequest request){
        if(implMap == null){
            return null;
        }
        String className = request.getClassName();
        String methodName = request.getMethodName();
        try {
            String implClassName = implMap.get(className).getImpl();
            Class<?> clazz = Class.forName(implClassName);
            Object obj = clazz.newInstance();
            Method mehtod = clazz.getMethod(methodName,request.getParamTypes());
            return mehtod.invoke(obj,request.getParamsVals());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
