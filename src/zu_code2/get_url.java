package zu_code2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by cxyu on 17-6-16.
 */
//获取url
public class get_url implements Runnable {

    //已经得到的入了没有得到的url
    static Set<String> get  = Collections.synchronizedSet(new HashSet<String>());
    static Set<String> no_get  = Collections.synchronizedSet(new HashSet<String>());

    //获取百度知道的内容
    public static void get(String url){
        String question_content="";
        String question="";
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0")
                    .get();
            Elements lis=doc.select("#j-question-list-pjax-container > div > ul > li");
            System.out.println("获取一页,现有链接数"+ get_url.no_get.size());
            for(Element li:lis){
                String URL = li.select("div > div > a").attr("href");
                //System.out.println(URL);
                if(!get_url.get.contains(URL)&&!get_url.no_get.contains(URL))
                    get_url.no_get.add(URL);
            Thread.sleep(10000);
            //10秒钟获取一次已经很占端口
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //通过提供的单词生成url
    public static List<String> createurl(){
        List<String> urls=new ArrayList<String>();



        return urls;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(get_url.no_get.size()>1000)
                    Thread.sleep(1000);
                get("https://zhidao.baidu.com/list?type=feed");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){
    //https://zhidao.baidu.com/list?type=feed
        for (;;)
    get("https://zhidao.baidu.com/list?type=feed");
    }
}
