package main.java.cxyu_spider;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.jsoup.nodes.Document;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.InetAddress;
import java.util.*;

/**
 * Created by cxyu on 17-6-4.
 */
public class Spider {
    //参数集
    Get_set get_set=null;
    int get_thread=1;
    //获取页面的参数，及获取页面的线程数
    Page_set page_set=null;
    int page_thread=1;
    //提取页面的参数，及页面的线程数
    String[] urls={};
    //页面的链接数

    //redis的链接池
    static  Jedis Jedis=null;

    static  String jedis_set_get;
    static  String jedis_set_no;

    static  String jedis_ip;
    static  Integer jedis_port;

    //一次设置一直不变
    static boolean ip=false;
    //是否启动ip模块
    static boolean elastic_serch=false;
    //是否启动elastic search模块
    static String index="";

    //elastic search的设置
    static String elastic_user="";
    static String elastic_pd="";
    static String elastic_ip="";
    static Integer elastic_port;
    static TransportClient client;



    //核心模块参数集
    //static List<Document> documents = Collections.synchronizedList(new ArrayList<Document>());
    static Map<String, Document> documents = Collections.synchronizedMap(new HashMap<String,Document>());
    //存储所有的下载页面

    static List<HashMap<String, String>> content = Collections.synchronizedList(new ArrayList<HashMap<String, String>>());
    //static Map<String, String> content = Collections.synchronizedMap(new HashMap<String,String>());
    //存储最后的元素的集合,数据以链表形式存储，及一页一个map，


    //存储提取页面的元素

    static List<String> get = Collections.synchronizedList(new ArrayList<String>());
    //存储所有的未获取链接

    static List<String> no_get = Collections.synchronizedList(new ArrayList<String>());
    //存储所有的已经获取的链接









    //最后通过run来运行函数
    public void run() {
        //设置redis的set名字
        if(Jedis!=null) {
            this.jedis_set_get = this.urls[0] + "_get";
            this.jedis_set_no = this.urls[0] +"_no";
        }


        //判断get_set是否为空来挑选运行哪个函数
        if(get_set!=null) {
            Get_page Get_page = new Get_page();
            Get_page.addUrl_new(this.get_thread, this.get_set, this.urls);
        }
        else {
            Get_page Get_page = new Get_page();
            Get_page.addUrl_new(this.get_thread, this.urls);
        }
        //运行提取函数
        Page page=new Page(this.page_set,this.page_thread);
        //保存部分
        if(Spider.elastic_serch)
        {
            //检查索引有无，只一次。
            try {
                lianjie lianjie=new lianjie();
                this.client=lianjie.getClient(Spider.elastic_user,Spider.elastic_pd,Spider.elastic_ip,Spider.elastic_port);
                lianjie.cheak_index(index,this.client);
                //启动保存数据代码
                lianjie.save_begin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //页面的元素选择
    public static Spider pageset(Page_set page_set,int thread){
        return new Spider(page_set,thread);
    }



    public Spider(Page_set page_set,int thread){
        this.page_set=page_set;
        this.page_thread=thread;
    }

    public Spider ipset(){
        this.ip=true;
        if(true)
            main.java.get_ip.get.getip_start();
        //只能开启一次
        return this;
    }

    //因为每次使用同一链接出问题，需要多次开关
    public  static Jedis redis_get(){
        Redis redis=new Redis(Spider.jedis_ip,Spider.jedis_port);
        return redis.jedis;
    }

    //获得到redis的链接接口
    public Spider redis_set(String ip,Integer port){
        this.jedis_ip=ip;
        this.jedis_port=port;
        Redis redis=new Redis(ip,port);
        this.Jedis=redis.jedis;
        return this;
    }

    public Spider elastic_save(String user,String password,String ip,Integer port,String index){
        this.elastic_serch=true;
        this.index=index;
        this.elastic_user=user;
        this.elastic_pd=password;
        this.elastic_ip=ip;
        this.elastic_port=port;


        return this;
    }

    //添加getset
    public  Spider getset(Get_set get_set){
        this.get_set=get_set;
        return this;
    }



    //添加url
    public Spider addurl(int thread,String... urls) {
        String[] var2 = urls;
        int var3 = urls.length;
        this.urls=urls;
        this.get_thread=thread;
        for(int var4 = 0; var4 < var3; ++var4) {
            String url = var2[var4];
            this.no_get.add(url);
        }
        return this;
    }
//通过输入参数，然后在run里面运行。

    //getpage的参数设置，select的设置，spider的添加，
    public static void addUrl(String url, Get_set get_set, int thread) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(url,get_set,thread);
    }

    public static void addUrl(List<String> urls, Get_set get_set, int thread) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(urls,get_set,thread);
    }

    public static void addUrl(String url, int thread) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(url,thread);
    }


    public static void addUrl(List<String> urls, int thread) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(urls,thread);
    }


    public static void addUrl(String url, Get_set get_set) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(url,get_set);
    }

    public static void addUrl(List<String> urls, Get_set get_set) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(urls,get_set);
    }

    public static void addUrl(String url) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(url);
    }


    public static void addUrl(List<String> urls) {
        Get_page Get_page =new Get_page();
        Get_page.addUrl(urls);
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
                .addurl(1,"http://3g.ifeng.com/")
                .elastic_save("DeepSearch&","elastic:YangZC*#03","124.127.117.108",9300,"cxyu")
                .run();
    }


}
