package main.java.cxyu_spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by cxyu on 17-6-6.
 */
public class do_something implements Page_set {

    public void get_wenku(){
        try {
            Document s=Jsoup
                    .connect("https://wk.baidu.com/view/b68eb7087cd184254b3535a5?pcf=2")
                    .userAgent("User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)")
                    .timeout(30000)
                    .get();
            Elements ps=s.select("p");
            for(Element p:ps)
                System.out.println(p.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        String[] url=new String[19];
        for(int i=1;i<20;i++){
            url[i-1]= "http://ip.chinaz.com/getip.aspx";
        }

        Get_set get_set=new
                Get_set(3000,0,"Mozilla/4.0 (compatible; MSIE 7.0 ; Windows NT 6.0)");

        Spider.pageset(new do_something(),1)
                .getset(get_set)
                .addurl(1,"http://sports.ifeng.com/","","")

                //.redis_set("127.0.0.1",6379)
                .run();
    }

    public void process(Document document) {
       System.out.println("get here   "+document.title());
       String title=document.title();

       Document_cxyu document_cxyu=new Document_cxyu();

       HashMap<String,String> map=new HashMap<String,String>();
       map.put("title",title);
       document_cxyu.save(map);
       for(String url:document_cxyu.geturl(document)){
           document_cxyu.addurl(url);

       }
    }
}
