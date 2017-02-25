package rpc.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by kfzx-liuyz1 on 2016/12/7.
 */
public class XmlFileUtil {


    /**
     * java 转换成xml
     * @Title: toXml
     * @Description: TODO
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj){
        XStream xstream=new XStream();
//          XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
//          XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释

        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
        xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        xstream.aliasSystemAttribute(null, "class");
        return xstream.toXML(obj);
    }

    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean
     * @Description: TODO
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     *
     * 调用的方法实例：PersonBean person=XmlFileUtil.toBean(xmlStr, PersonBean.class);
     */
    @SuppressWarnings("unchecked")
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		// 注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError:
		// org/xmlpull/v1/XmlPullParserFactory
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

    /**
     * 写到xml文件中去
     * @Title: writeXMLFile
     * @Description: TODO
     * @param obj 对象
     * @param outputStream  输出流
     * @return boolean
     */

    public static void toXMLFile(Object obj, OutputStream outputStream ){
        String strXml = toXml(obj);
        try {
            outputStream.write(strXml.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从xml文件读取报文
     * @Title: toBeanFromFile
     * @Description: TODO
     * @param absPath 绝对路径
     * @param fileName 文件名
     * @param cls
     * @throws Exception
     * @return T
     */
    @SuppressWarnings({ "unchecked"})
	public static <T> T  toBeanFromFile(String absPath, String fileName,Class<T> cls) throws Exception{
        String filePath = absPath +fileName;
        InputStream ins = null ;
        try {
            ins = new FileInputStream(new File(filePath ));
        } catch (Exception e) {
            throw new Exception("读{"+ filePath +"}文件失败！", e);
        }

        //String encode = useEncode(cls);
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj =null;
        try {
            obj = (T)xstream.fromXML(ins);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("解析{"+ filePath +"}文件失败！",e);
        }
        if(ins != null)
            ins.close();
        return obj;
    }

}