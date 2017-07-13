package baidubaike;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxyu on 17-6-22.
 */
public class Get_flie {
    public static List<String> getname(String path){
        List<String> name=new ArrayList<String>();
        File file=new File(path);
        File[] files=file.listFiles();
        for(File one:files)
            name.add(one.getName());
        return name;
    }

    public static List<String> havano(List<String> all,List<String> get){
        for(String a: get)
        all.remove(a);
        return all;
    }


    public static void main(String[] args) throws Exception {
       List<String> names=getname("/home/cxyu/tmp");

    }
}

