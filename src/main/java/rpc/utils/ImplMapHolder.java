package rpc.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kfzx-liuyz1 on 2017/2/24.
 */
@XStreamAlias("root")
public class ImplMapHolder{

    @XStreamAlias("mappings")
    private List<ImplMap> implMapList;

    public List<ImplMap> getImplMapList() {
        return implMapList;
    }

    public void setImplMapList(List<ImplMap> implMapList) {
        this.implMapList = implMapList;
    }

    public Map<String,ImplMap> toMap(){
        Map<String,ImplMap> map = new HashMap<String, ImplMap>();
        this.implMapList.stream().forEach(impl -> {map.put(impl.getIntface(),impl);});
        return map;
    }
}
