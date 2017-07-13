package main.java.cxyu_spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by cxyu on 17-6-5.
 */
public class Page implements Runnable {
    Page_set page_set=null;
    String thread_w="asdfadsf";
    //外部从哪里传入数据
    //处理程序，获取数据
    public  Page(Page_set page_set,int thread){
        this.thread_w=thread+"";
        System.out.println(this.thread_w);
        this.page_set=page_set;
        if(this.page_set==null)
            System.out.println("zheli chucuo");

        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }



    //一直运行这个程序。
    public void run() {
        while (true) {
            try{
                if (Spider.documents.size()==0||Spider.content.size()>1000)
                    Thread.sleep(3000);
                //当发现没有数据时，休息一会
                Document value=null;
                Iterator<String> iter=Spider.documents.keySet().iterator();
                while (iter.hasNext()){
                    String key=iter.next();
                    value=Spider.documents.get(key);
                    Spider.documents.remove(key);
                    break;
                    //就读取一个然后就停止
                }
                //运行使用者写的代码。

                this.page_set.process(value);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){
        try {
            for(int i=1;i<15;i++){
            Document doc= Jsoup.connect("http://search.sina.com.cn/?q=%E9%9B%84%E5%AE%89&c=news&" +
                    "from=channel&ie=utf-8&col=&range=&source=&country=&size=&time=&a=&" +
                    "page="+i+"&pf=2131425459&ps=2134309112&dpc=1")
                    .userAgent("")
                    .get();

            String name=doc.getClass().getName();
            System.out.println(name);
            Document docs=(Document) doc;
            Spider.documents.put(""+i,docs);
            }
            //Page page=new Page();
            //page.pageset(new do_something(),2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
