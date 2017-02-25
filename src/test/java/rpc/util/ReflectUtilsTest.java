package rpc.util;

import org.junit.Test;
import rpc.utils.ImplMap;
import rpc.utils.ImplMapHolder;
import rpc.utils.ReflectUtils;
import rpc.utils.XmlFileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kfzx-liuyz1 on 2017/2/24.
 */
public class ReflectUtilsTest {

    @Test
    public void genXml(){
        List<ImplMap> implMaps = new ArrayList<ImplMap>();
        ImplMap map = new ImplMap();
        map.setIntface("rpc.test.Test");
        map.setImpl("rpc.test.TestImpl");
        implMaps.add(map);
        ImplMapHolder implMapHolder = new ImplMapHolder();
        implMapHolder.setImplMapList(implMaps);
        System.out.println(XmlFileUtil.toXml(implMapHolder));


    }

    @Test
    public void xmltest(){
        //ImplMapHolder implMaps = ReflectUtils.getImplMappings();
        //System.out.println(implMaps.toString());
    }
}
