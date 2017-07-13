package main.java.cxyu_spider;

/**
 * Created by cxyu on 17-6-5.
 */
public class Screen_url {
    public static void select(String url){
        if(Spider.Jedis==null){
        if((!Spider.get.contains(url))&(!Spider.no_get.contains(url)))
            Spider.no_get.add(url);}
        else {
            if((!Spider.redis_get().sismember(Spider.jedis_set_get, url))
                    &(!Spider.redis_get().sismember(Spider.jedis_set_no,url)))
            Spider.redis_get().sadd(Spider.jedis_set_no,url);
        }
    }
    public static  void main(String args[]){
        Spider.no_get.add("adf");
        Spider.get.add("adasdff");
        System.out.println(Spider.no_get.size());
        select("adasdff");
        System.out.println(Spider.no_get.size());
    }
}
