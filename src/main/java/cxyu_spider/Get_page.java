package main.java.cxyu_spider;

import main.java.get_ip.get;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by cxyu on 17-6-5.
 */
public class Get_page implements Runnable {

    static String url;
    static Get_set get_set;
    static List<String> urls;
    //因为run中无法输入参数，需要从这里输入。

    //得到页面，还需要标记得到的页面是什么，是哪里得到的


    // 放到多线程里面跑
    public static void addurl(String url, Get_set get_set) {

        //什么时候停止
        //到时主函数检查所有参数为空时退出。
        while (true) {
            try {
                //当发现没有链接时，爬虫休息
                if (Spider.no_get.size() == 0||Spider.documents.size()>1000)
                    Thread.sleep(10000);
                //进行删除url，以及添加url操作
                url = Spider.no_get.remove(0);
                Spider.get.add(url);

                Document doc = Jsoup.connect(url)
                        .userAgent(get_set.useragent)
                        .timeout(get_set.timeout)
                        .maxBodySize(get_set.max)
                        .get();
                Spider.documents.put(url, doc);
                System.out.println("get:" + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //设置这个try-catch防止函数被终止
        }
    }

    public static void addurl(List<String> urls, Get_set get_set) {
        while (true) {
            try {
                //当发现没有链接时，爬虫休息
                if(Spider.Jedis!=null){
                if (Spider.redis_get().smembers(Spider.jedis_set_no).size()==0)
                    Thread.sleep(3000);}
                else {
                    if (Spider.no_get.size() == 0)
                        Thread.sleep(3000);
                }
                //设置ip
                if(Spider.ip){
                    if(get.cheak_ip.size()==0)
                        Thread.sleep(10000);
                    Random random = new Random();
                    int random_int=random.nextInt(10);
                    String ip=get.cheak_ip.get(random_int).split(":")[0];
                    String prot=get.cheak_ip.get(random_int).split(":")[1];
                    System.getProperties().setProperty("http.proxyHost", ip);
                    System.getProperties().setProperty("http.proxyPort", "80");
                }
                String url=null;
                //进行删除url，以及添加url操作
                if(Spider.Jedis==null){
                    url = Spider.no_get.remove(0);
                    Spider.get.add(url);}
                else
                {
                    url = Redis.geturl(Spider.jedis_set_no,Spider.redis_get());
                    System.out.println(url);
                    Spider.redis_get().sadd(Spider.jedis_set_get,url);
                }
                System.out.println(url);
                Document doc = Jsoup.connect(url)
                        .userAgent(get_set.useragent)
                        .timeout(get_set.timeout)
                        .maxBodySize(get_set.max)
                        .get();
                Spider.documents.put(url, doc);
                System.out.println("get:" + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void addurl(String url) {

        while (true) {
            try {
                //当发现没有链接时，爬虫休息
                if (Spider.no_get.size() == 0)
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                //进行删除url，以及添加url操作
                url = Spider.no_get.remove(0);
                Spider.get.add(url);
                Document doc = Jsoup.connect(url)
                        .get();
                Spider.documents.put(url, doc);
                System.out.println("get:" + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public static void addurl(List<String> urls) {

        while (true) {
            try {
                //当发现没有链接时，爬虫休息
                if(Spider.Jedis!=null){
                    if (Spider.redis_get().smembers(Spider.jedis_set_no).size()==0)
                        Thread.sleep(10000);}
                else {
                    if (Spider.no_get.size() == 0)
                        Thread.sleep(10000);
                }


                //设置ip
                if(Spider.ip){
                    if(get.cheak_ip.size()==0)
                        Thread.sleep(10000);
                    Random random = new Random();
                    int random_int=random.nextInt(10);
                    String ip=get.cheak_ip.get(random_int).split(":")[0];
                    String prot=get.cheak_ip.get(random_int).split(":")[1];
                    System.getProperties().setProperty("http.proxyHost", ip);
                    System.getProperties().setProperty("http.proxyPort", "80");
                }
                String url=null;
                //进行删除url，以及添加url操作
                if(Spider.Jedis==null){
                url = Spider.no_get.remove(0);
                Spider.get.add(url);}
                else
                {
                    url = Redis.geturl(Spider.jedis_set_no,Spider.redis_get());
                    Spider.redis_get().sadd(Spider.jedis_set_get,url);
                }
                Document doc = Jsoup.connect(url)
                        .get();
                Spider.documents.put(url, doc);
                System.out.println("get:" + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //getpage的参数设置，select的设置，spider的添加，
    public  void addUrl(String url, Get_set get_set, int thread) {
        selector = 1;
        Get_page.url = url;
        Get_page.get_set = get_set;
        Spider.no_get.add(url);
        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }

    public  void addUrl(List<String> urls, Get_set get_set, int thread) {
        selector = 2;
        Get_page.urls = urls;
        Get_page.get_set = get_set;
        Spider.no_get.addAll(urls);
        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }

    public  void addUrl(String url, int thread) {
        selector = 3;
        Get_page.url = url;
        Spider.no_get.add(url);
        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }


    public  void addUrl(List<String> urls, int thread) {
        selector = 4;
        Get_page.urls = urls;
        Spider.no_get.addAll(urls);
        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }


    public  void addUrl(String url, Get_set get_set) {
        selector = 1;
        Get_page.url=url;
        Get_page.get_set=get_set;
        Spider.no_get.add(url);
        for (int i = 0; i < 1; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }

    public  void addUrl(List<String> urls, Get_set get_set) {
        selector = 2;
        Get_page.urls=urls;
        Get_page.get_set=get_set;
        Spider.no_get.addAll(urls);
        for (int i = 0; i < 1; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }

    public  void addUrl(String url) {
        selector = 3;
        Get_page.url=url;
        Spider.no_get.add(url);
        for (int i = 0; i < 1; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }


    public void addUrl(List<String> urls) {
        selector = 4;
        Get_page.urls=urls;
        Spider.no_get.addAll(urls);
        for (int i = 0; i < 1; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }


    //上面的代码已经不能用了，以这里的代码为主
    public void addUrl_new(int thread,String... urls){
        selector = 4;
        this.urls = Arrays.asList(urls);
        if(Spider.Jedis==null)
        Spider.no_get.addAll(this.urls);
        else{
            for(String url:this.urls)
            Spider.redis_get().sadd(Spider.jedis_set_no,url);

        }
        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }

    public void addUrl_new(int thread,Get_set get_set,String... urls){
        selector = 2;
        this.urls = Arrays.asList(urls);
        this.get_set=get_set;
        if(Spider.Jedis==null)
            Spider.no_get.addAll(this.urls);
        else{
            for(String url:this.urls)
                Spider.redis_get().sadd(Spider.jedis_set_no,url);
        }
        for (int i = 0; i < thread; i++) {
            Thread one = new Thread(this);
            one.start();
        }
    }



    public void run() {
        switch (selector) {
            case 1:
                addurl(this.url, this.get_set);
                break;
            case 2:
                addurl(this.urls, this.get_set);
                break;
            case 3:
                addurl(this.url);
                break;
            case 4:
                addurl(this.urls);
                break;
            default:
                break;
        }
        addurl("");
    }

    static int selector = 0;


    public static void main(String args[]) {
        Get_set get_set=new
                Get_set(3000,0,"User-Agent:Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)");
        List<String> url=new ArrayList<String>();
        for(int i=0;i<17;i++)
        url.add("http://search.sina.com.cn/?q=%C2%FE%BB%AD&range=all&c=news&sort=time&col=&source=&" +
                        "from=&country=&size=&time=&a=&page="+i+"&pf=2131425470&ps=2134309112&dpc=1");
        String[] urls = (String[])url.toArray(new String[17]);
        Get_page Get_page = new Get_page();
        Get_page.addUrl_new(3,get_set,"http://www.baidu.com");
    }


}
