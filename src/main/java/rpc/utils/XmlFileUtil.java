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
     * java ת����xml
     * @Title: toXml
     * @Description: TODO
     * @param obj ����ʵ��
     * @return String xml�ַ���
     */
    public static String toXml(Object obj){
        XStream xstream=new XStream();
//          XStream xstream=new XStream(new DomDriver()); //ֱ����jaxp dom������
//          XStream xstream=new XStream(new DomDriver("utf-8")); //ָ�����������,ֱ����jaxp dom������

        ////���û����䣬xml�еĸ�Ԫ�ػ���<��.����>������˵��ע�������û��Ч�����Ե�Ԫ���������������
        xstream.processAnnotations(obj.getClass()); //ͨ��ע�ⷽʽ�ģ�һ��Ҫ����仰
        xstream.aliasSystemAttribute(null, "class");
        return xstream.toXML(obj);
    }

    /**
     *  ������xml�ı�ת����Java����
     * @Title: toBean
     * @Description: TODO
     * @param xmlStr
     * @param cls  xml��Ӧ��class��
     * @return T   xml��Ӧ��class���ʵ������
     *
     * ���õķ���ʵ����PersonBean person=XmlFileUtil.toBean(xmlStr, PersonBean.class);
     */
    @SuppressWarnings("unchecked")
	public static <T> T toBean(String xmlStr, Class<T> cls) {
		// ע�⣺����new Xstream(); ���򱨴�java.lang.NoClassDefFoundError:
		// org/xmlpull/v1/XmlPullParserFactory
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(cls);
		T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}

    /**
     * д��xml�ļ���ȥ
     * @Title: writeXMLFile
     * @Description: TODO
     * @param obj ����
     * @param outputStream  �����
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
     * ��xml�ļ���ȡ����
     * @Title: toBeanFromFile
     * @Description: TODO
     * @param absPath ����·��
     * @param fileName �ļ���
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
            throw new Exception("��{"+ filePath +"}�ļ�ʧ�ܣ�", e);
        }

        //String encode = useEncode(cls);
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj =null;
        try {
            obj = (T)xstream.fromXML(ins);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new Exception("����{"+ filePath +"}�ļ�ʧ�ܣ�",e);
        }
        if(ins != null)
            ins.close();
        return obj;
    }

}