package main.java.cxyu_spider;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by cxyu on 17-6-11.
 */
public class Redis {
    public Jedis jedis;//非切片额客户端连接
    public JedisPool jedisPool;//非切片连接池

    public Redis(String ip ,Integer port)
    {
        initialPool(ip,port);
        jedis = jedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool(String ip ,Integer port)
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxIdle(5);

        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config,ip,port);
    }

    private void Setadd(String set,String content)
    {
        jedis.sadd(set, content);
    }

    private void Setremove(String set,String content){
        jedis.srem(set, content);
    }
    //得到链接并删除得到的链接
    public static String geturl(String Set,Jedis jedis){
        Set<String> set = jedis.smembers(Set);
        Iterator<String> it=set.iterator() ;
        Object obj=new Object();
        while(it.hasNext()){
            obj=it.next();
            String content=obj.toString();
            System.out.println("从redis里得到     "+content);
            jedis.srem(Set,content);
            return obj.toString();
        }
        return  null;
    }

}
