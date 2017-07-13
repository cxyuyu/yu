package main.java.cxyu_spider;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;

/**
 * Created by cxyu on 17-6-5.
 */
public class Get_set{
    Integer timeout;
    //超市时间

    Integer max;
    //设置cookie
    //connection.maxBodySize(0)

    String useragent;




    //设置usergent


    //参数
    public Get_set(int timeout,int max, String useragent ){
        this.timeout=timeout;
        this.max=max;
        this.useragent=useragent;
    }

    public static Connection connect(String url) {
        return HttpConnection.connect(url);
    }

    public static void main(String args[]){

    }

}
