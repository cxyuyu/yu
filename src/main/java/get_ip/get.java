package main.java.get_ip;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by cxyu on 17-6-2.
 */
//五分钟获取一次，检查能用后，放到list
    //只获取头一页的数据，日后进行修改
public class get implements Runnable{

    public static List<String>  get_ip(){
        List<String> ips=get_intent();
        ips.addAll(get_intent2());
        //ips.addAll(get_intent3());
        List<String> ips_use=new ArrayList<String>();
        for(String ipap:ips){
            String ip=ipap.split(":")[0];
            String port=ipap.split(":")[1];
            boolean get=cheak(ip,port);
            if(get){
                ips_use.add(ipap);}
        }
        return ips_use;
    }

    public static List<String> change(String[] a){
        List<String> ips=new ArrayList<String>();
        for(int i=0;i<a.length;i++){
            ips.add(a[i]);
        }
        return ips;
    }

    public static boolean cheak(String ip,String port){
        Document doc = null;
        try {

            System.getProperties().setProperty("http.proxyHost", ip);
            System.getProperties().setProperty("http.proxyPort", port);


            doc = Jsoup.connect("http://ip.chinaz.com/getip.aspx")
                    .userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)")
                    .ignoreContentType(true)
                    .timeout(3000)
                    .get();
            String something = doc.select("body").text();
            String ip_getcontent=something.split(",")[0];
            String ip_get=ip_getcontent.substring(5,ip_getcontent.length()-1);
            if(ip_get.equals(ip)){
                System.out.println("success");
                return true;
            }
        } catch (Exception e) {
           // e.printStackTrace();
        }
        return false;
    }

    //写大概四个函数，用来获取ip，
    //每个返回一个list
    public static List<String>  get_intent(){
        List<String> ips=new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.ip181.com")
                    .userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)")
                    .ignoreContentType(true)
                    .timeout(3000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements all = doc.select("body > div:nth-child(3) > " +
                "div.panel.panel-info > div.panel-body > div > div:nth-child(2) > table > tbody > tr");
        for(int i=1;i<all.size();i++){
           Element ip =all.get(i);
           String IP= ip.select("td").first().text();
           String PORT=ip.select("td").get(1).text();
           String ipap=IP+":"+PORT;
           ips.add(ipap);
        }

        return ips;
    }

    public static List<String>  get_intent2(){
        List<String> ips=new ArrayList<String>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://api.xicidaili.com/free2016.txt")
                    .userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)")
                    .ignoreContentType(true)
                    .timeout(3000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String all = doc.select("body").text();
        String ipall[]=all.split(" ");
        for(String s:ipall){
            ips.add(s);
        }
        return ips;
    }

    public static List<String>  get_intent3(){
        List<String> ips=new ArrayList<String>();
        Document doc = null;
        try {
            for(int q=1;q<11;q++){
            doc = Jsoup.connect("http://www.kuaidaili.com/proxylist/"+q)
                    .userAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)")
                    .ignoreContentType(true)
                    .timeout(3000)
                    .get();
            Elements all = doc.select("#index_free_list > table > tbody > tr");
            for(int i=0;i<all.size();i++){
                Element ip =all.get(i);
                String IP= ip.select("td").first().text();
                String PORT=ip.select("td").get(1).text();
                String ipap=IP+":"+PORT;
                ips.add(ipap);
            }}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ips;
    }

    // 有静态值
    public static List<String> all_ip = Collections.synchronizedList(new ArrayList<String>());
    public static List<String> cheak_ip = Collections.synchronizedList(new ArrayList<String>());
    public static int where;//记录运行到哪一个了
    //实施检查
    public void run() {
        for(;where>(0-1);where--){
            String ipap=all_ip.get(where);
            System.out.println(ipap);
            String ip=ipap.split(":")[0];
            String port=ipap.split(":")[1];
            boolean get=cheak(ip,port);
            //当这个ip不能用时，进行自减。
            if(!get){
             remove(ipap);
            }
        }

    }

    //自减某元素,不用位置i，防止多线程出问题
    public static void remove(String ipap){
        try{
        for(int i=0;i<all_ip.size();i++){
            if(all_ip.get(i).equals(ipap)){
                all_ip.remove(i);
                break;
            }
        }}
        catch (Exception e){

        }
    }

    //去重模块
    public static List<String> listuniq(){
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = all_ip.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        all_ip=newList;
        return newList;
    }



    //此类包括三个部分，获取，检查，写入
    //只是检查部分
    public static void get_ip_cheak(){
        //网上获取的，之前可以用的，
        all_ip.addAll(cheak_ip);


        all_ip.addAll(get_intent());
        all_ip.addAll(get_intent2());
//        all_ip.addAll(get_intent3());
        listuniq();

        get get=new get();

        where=all_ip.size()-1;
        for(int i=0;i<3;i++){
        Thread one=new Thread(get);
        one.start();}
        //运行完毕后一次性赋值
        //等待上面的代码运行完毕。运行完后进行保存。


        while(true) {
            if (where < 0)
            {

                cheak_ip = Collections.synchronizedList(new ArrayList<String>());
                for (String s : all_ip)
                    cheak_ip.add(s);
                all_ip = Collections.synchronizedList(new ArrayList<String>());
                //五分钟写入一次。
                String content = "";
                for (int i = 0; i < cheak_ip.size(); i++)
                    if (i == 1)
                        content = cheak_ip.get(i);
                    else
                        content = content + "\n" + cheak_ip.get(i);
                appendMethodB("/home/cxyu/tmp/spider/ip", content);
                break;
            }
            //一秒钟检查一次。
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //外部启动模块
    //一个定时装置
    //除了读取文件之外，其他都是另一个线程跑着
    public static void getip_start(){
        List<String> a=readFileByLines("/home/cxyu/tmp/spider/ip");
        //只是第一次这么运行
        for(String s: a)
            cheak_ip.add(s);
        //不断重复运行此函数，这是另外一个线程。主函数不应该因为这个被中断。
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<String> future =
                new FutureTask<String>(new Callable<String>() {
                    public String call() {
                        for(int i=0;i<Integer.MAX_VALUE;i++){
                            get_ip_cheak();
                            try {
                                //休息十分钟
                                //所有代码运行失败也不会超过十分钟
                                Thread.sleep(600000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }}
                        return null;
                    }});
        executor.execute(future);

    }


    //以行为读取单位，最后输出也是一行代表list中的一个元素
    public static List<String> readFileByLines(String fileName) {
        List<String> all=new ArrayList<String>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                all.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return all;
    }

    //直接重写ip，不进行追加
    public static void appendMethodB(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //打算先运行一个
    public static void main(String args[]){
        getip_start();
        for(;;){
            System.out.println("cheak_ip"+cheak_ip.size());
            System.out.println("all_ip"+all_ip.size());
            System.out.println("where:"+where);
//            if(all_ip.size()<9)
//                for (String s:all_ip)
//                    System.out.println(s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
