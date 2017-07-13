package com163;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cxyu on 17-5-12.
 */
public class w163  {


    public static List<String>  get_id(String Url,String Url2) throws IOException {
        List<String> all=new ArrayList<String>();
        List<String> all_doc=new ArrayList<String>();
        for (int i = 0; ; i++) {
            Document doc = Jsoup
                    .connect(Url+30*i+Url2)
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .ignoreContentType(true)//因为数据有xml，所以添加这个
                    .maxBodySize(0)//担心数据过长，无法获取。
                    .get();
            String comments = doc.body().text();
            System.out.println(comments.length());
            if(comments.length()<100)
                break;
            all_doc.add(comments);
        }
        all=get_id_content(all_doc);
        return all;
    }

    //从网页中抽取，评论
    public static List<String> get_id_content(List<String> INPUT_all){
        List<String> all=new ArrayList<String>();
        String REGEX = "\"content\".*?\"createTime\":";
        Pattern p = Pattern.compile(REGEX);
        for(String INPUT:INPUT_all){
            Matcher m = p.matcher(INPUT); // 获取 matcher 对象
            while(m.find()) {
                String comment=INPUT.substring(m.start()+11,m.end()-15);
                if(have(all,comment)){
                    System.out.println(comment);
                    all.add(comment);}
            }}
        return all;
    }

    public static String  get_url(String Url) throws IOException {
        Document doc = Jsoup
                .connect(Url)
                .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                .get();
        String url=doc.select("#post_comment_area > " +
                "div.post_comment_toolbar > div.post_comment_joincount > a")
                .attr("href");
        System.out.println("url"+url);
        return url;
    }


    public static List<String>  get_comments(String Url,String Url2) throws IOException {
        List<String> all=new ArrayList<String>();
        List<String> all_doc=new ArrayList<String>();
        for (int i = 0; ; i++) {
            Document doc = Jsoup
                    .connect(Url+30*i+Url2)
                    .userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
                    .ignoreContentType(true)//因为数据有xml，所以添加这个
                    .maxBodySize(0)//担心数据过长，无法获取。
                    .get();
            String comments = doc.body().text();
            if(comments.length()<100)
                break;
            all_doc.add(comments);
        }
        all=get_comment(all_doc);
        return all;
    }
    //从网页中抽取，评论
    public static List<String> get_comment(List<String> INPUT_all){

        List<String> all=new ArrayList<String>();
        String REGEX = "\"content\".*?\"createTime\":";
        Pattern p = Pattern.compile(REGEX);
        for(String INPUT:INPUT_all){
        Matcher m = p.matcher(INPUT); // 获取 matcher 对象
        while(m.find()) {
            String comment=INPUT.substring(m.start()+11,m.end()-15);
            if(have(all,comment)){
            System.out.println(comment);
            all.add(comment);}
        }}
        return all;
    }

    //去除重复的数据
    public static boolean  have(List<String> all,String comment){
        for(String one:all)
            if(comment.equals(one))
                return false;
        return true;
    }



    //一条一条链接往下刷，然后获取评论，得有两个线程。
    //一个新闻的评论多线程较为麻烦，
    //先写一个一个新闻的评论的获取。
    public static void main(String[] args) {

//        try {
//            get_comments("http://comment.news.163.com/api/v1/products/a286967" +
//                    "4571f77b5a0867c3d71db5856/threads/CK643EK3000187VE/comments/ne" +
//                    "wList?offset=" ,
//                    "&limit=30&showLevelThreshold=72&headLimit=1&tai" +
//                    "lLimit=2&callback=getData&ibc=newspc&_=1494577122986");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try{
            get_id("http://temp.163.com/special/0080" +
                    "4KVA/cm_shehui_",".js?callback=data_callback");
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}