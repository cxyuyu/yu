package main.java.cxyu_spider;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cxyu on 17-6-6.
 */
public class Document_cxyu  {

    //添加url
    public Document_cxyu addurl(String url){
        Screen_url.select(url);
        return this;
    }

    //保存数据
    public Document_cxyu save(HashMap<String,String> content){
        if(Spider.elastic_serch){
        Spider.content.add(content);
        }
        return this;
    }

    //获取所有链接
    public List<String> geturl(Document doc){
        List<String> urls=new ArrayList<String>();
        Elements links = doc.select("a[href]");
        for(Element link:links)
        {
            String url=link.attr("href");
            if(url.contains("http"))
                urls.add(url);
        }
        return urls;
    }

}
