package main.java.redis;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.io.IOException;

/**
 * Created by cxyu on 17-6-11.
 */

public class as implements PageProcessor{
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);



    @Override
    public void process(Page page) {
        Document doc= null;
        try {
            doc = Jsoup.connect(page.getUrl().toString()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            System.out.println(link.attr("href"));
        }
        page.addTargetRequests(page.getHtml().links().regex("(https://\\.com/[\\w\\-]+/[\\w\\-]+)").all());
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        String url[]={"http://news.baidu.com/","http://www.ifeng.com","http://sh.qihoo.com/"};
        Spider.create(new as())
                .addUrl(url)
                .setScheduler(new RedisScheduler("127.0.0.1"))
                .run();
    }
}
