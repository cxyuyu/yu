package baidubaike;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by cxyu on 17-6-22.
 */
public class Get_2 {

    //得到map，下载保存
    public static void down(Map<String, String> all) {

        Iterator iter = all.entrySet().iterator();
        while (iter.hasNext()) {
            try {


            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();


            //下载
            Document document= null;
            try {
                document = Jsoup.connect(val.toString().toString())
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, " +
                                "like Gecko) Chrome/58.0.3029.110 Safari/537.36").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(document.toString().length());
            //保存数据,
            get.appendMethodB("/home/cxyu/tmp/bdbk/"+key.toString(),document.toString());
            //提取数据进行保存
            String get_content=get.get_ci(key.toString().split("：")[0],document);
            get.appendMethodB("/home/cxyu/tmp/bdbk-ci/"+key.toString(),get_content);
            //文档里面还是以中国：古代地理这种形式保存
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //得到多义词的数据，并下载保存
    public static Map<String, String> get_dy(String content) {
        Map<String, String> words = new HashMap<String, String>();
        //保存词名与链接，第一个是词名，第二个是链接

        try {
            Document document = Jsoup.connect("http://baike.baidu.com/item/" + content)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, " +
                            "like Gecko) Chrome/58.0.3029.110 Safari/537.36").get();
            Elements lis = document.select("body > div.body-wrapper > " +
                    "div.content-wrapper > div > div.main-content > ul > li");
            for (Element li : lis) {
                //body > div.body-wrapper > div.content-wrapper
                // > div > div.main-content > ul > li:nth-child(1) > div > a
                String id = li.select("div > a").attr("data-lemmaid");
                String url = "http://baike.baidu.com/item/" + content + "/" + id;
                String name = li.select("div > a").text();
                words.put(name, url);
                System.out.println(name + url);
            }
            //进行下载保存
            down(words);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;

    }


    public static void main(String args[]) {
        get_dy("癌症");

//        //去掉空格
//        String asd="书    名";
//        String[] s=asd.split(" ");
//        String sd="";
//        for(String a:s) {
//            System.out.println(a + "asdf");
//                sd+=a;
//
//        }
//        System.out.println(sd);
    }

}
