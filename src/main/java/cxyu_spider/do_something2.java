package main.java.cxyu_spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

/**
 * Created by cxyu on 17-6-9.
 */
public class do_something2 {
    public static void main(String args[]){

        Random r3 = new Random();
        System.out.println();
        System.out.println("使用种子缺省是当前系统时间的毫秒数的Random对象生成[0,10)内随机整数序列");
        for (int i = 0; i < 10000; i++) {
            System.out.println(r3.nextInt(10)+" ");
        }


        try {

            String url="http://www.ifeng.com/";
            Document doc = Jsoup.connect(url).userAgent("Mozilla/4.0 (compatible; MSIE 6.0; " +
                    "Windows NT 5.1; SV1; Acoo Browser; .NET CLR 1.1.4322; .NET CLR 2.0.50727) ").get();
            Elements links = doc.select("a[href]");
            for(Element link:links)
            {
                System.out.println(link.attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
