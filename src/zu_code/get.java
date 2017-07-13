package zu_code;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cxyu on 17-6-15.
 */

//一个问题一个回答，没有问题描述
public class get implements Runnable {



    //获取百度知道的内容
    public static void get_content(){
        String question="";

        String url = "";
        Iterator<String> itor = get_url.no_get.iterator();
        while (itor.hasNext()) {
            String lang = itor.next();
            System.out.println(lang);
            get_url.get.add(lang);
            get_url.no_get.remove(lang);
            url=lang;
            break;
        }

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0")
                    .get();

            question=doc.select("#wgt-ask > h1 > span").text();
            System.out.println(question);

            //得到内容，少于20字的，因为好回答基本大于20字
            Elements divs=doc.select("#wgt-answers > div.bd-wrap > div");
            String nei="123456789123456789000";


            for(Element div:divs)
            {
               nei=div.select("div > div > div > span").text().split("\\|")[0];
               System.out.println(nei);
               if(nei.length()<20){
                   System.out.println(nei);
                    break;
               }
            }

            //知道的有两种板式


            if(nei.length()>1&&nei.length()<21)
            creatfile(question,question+"\n"+nei);
           // write(question,question+"\n"+nei);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("出错的链接"+url);
        }
    }

    public static String get_content(Document document){
        String content="";
        document.select("wgt-best mod-shadow  ");//#best-answer-274974173
        return content;
    }


   // 创建文件夹
    public static void creatfile(String name,String content) {
        File file = new File("/home/cxyu/tmp/zhidao/"+name);
        boolean result = false;
        try {
            result = file.createNewFile();
            write(name,content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result) {
            System.out.println("create file successfully ");
        }

    }


    //文件读写
    public static void write(String name,String content) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("/home/cxyu/tmp/zhidao/"+name);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
            get_url.no_get.add("https://zhidao.baidu.com/question/82424437.html?fr=iks&word=%C3%C0%C5%AE&ie=gbk");
         //   get_url.no_get.add("https://zhidao.baidu.com/question/204023254239730205.html?fr=qlquick&entry=qb_list_feed&is_force_answer=0");
            get_content();
         // creatfile("asd","asdfasdfadfasdf");
    }

    @Override
    public void run() {
        while(true){
            try {
                if(get_url.no_get.size()==0)
                    Thread.sleep(9000);
                get_content();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
