package main.java.cxyu_spider;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by cxyu on 17-6-9.
 */
public class lianjie implements Runnable {
//    Settings settings = Settings.builder().put("cluster.name", "cxyu")
//            .put("xpack.security.user", "654987").build();
//    TransportClient client = new PreBuiltXPackTransportClient(settings)
//            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));

    //保存函数开始
    public void save_begin(){
        try {
            lianjie lianjie=new lianjie();
            for (int i = 0; i < 1; i++) {
                Thread one = new Thread(lianjie);
                one.start();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

//保存函数
    public void save_start(){
        while (true){
            try {
                if (Spider.content.size() == 0)
                    Thread.sleep(30000);
                HashMap<String,String> map;
                map=Spider.content.remove(0);
                lianjie lianjie=new lianjie();
                lianjie.ess(Spider.index,map,Spider.client);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    //得到客户端
    public TransportClient getClient(String user, String pd, String ip, Integer port) {
        TransportClient client;
        try {
            Settings settings = Settings.builder().put("cluster.name", user)
                    .put("xpack.security.user", pd).build();
            client = new PreBuiltXPackTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return getClient(user, pd, ip, port);

        }
    }


    //索引检查，无则添加
    public void cheak_index(String index, TransportClient client) {
        try {
            lianjie lianjie = new lianjie();
            if (!lianjie.isExistsIndex(index, client))
                //不存在则添加
                lianjie.createSimpleIndex(index, client);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //发生错误就重复，直到成功
            cheak_index(index, client);
        }
    }


    //判断索引是否存在，若存在则返回true ，不存在则返回false
    public boolean isExistsIndex(String indexName, TransportClient client) {
        try {
            IndicesExistsResponse response =
                    client.admin().indices().exists(
                            new IndicesExistsRequest().indices(new String[]{indexName})).actionGet();
            return response.isExists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断指定的索引的类型是否存在
     *
     * @param indexName 索引名
     * @param indexType 索引类型
     * @return 存在：true; 不存在：false;
     */
    public boolean isExistsType(String indexName, String indexType, TransportClient client) {
        TypesExistsResponse response =
                client.admin().indices()
                        .typesExists(new TypesExistsRequest(new String[]{indexName}, indexType)
                        ).actionGet();
        System.out.println(response);
        return response.isExists();
    }

    public lianjie() throws UnknownHostException {
    }

    public void ess(String index, HashMap<String, String> content, TransportClient client) throws IOException {
        //map多输入，少输入，都可以。
        Iterator<String> iter = content.keySet().iterator();
        //jsonbuild的数据，保存map的数据
        XContentBuilder contentBuilder = jsonBuilder().startObject();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = content.get(key);
            contentBuilder = contentBuilder.field(key, value);
        }
        contentBuilder = contentBuilder.endObject();

        //类型默认为properties，
        IndexResponse response2 = client.prepareIndex(index, "properties")
                .setSource(contentBuilder)
                .get();
        System.out.println("状态" + response2.getResult() + new Date());

        // shutdown
       // client.close();
    }

    public boolean createSimpleIndex(String index, TransportClient client) {
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        CreateIndexResponse response = indicesAdminClient.prepareCreate(index).get();
        return response.isAcknowledged();
    }

    //创建索引
    public boolean createIndex(String index, List<String> name, TransportClient client) {
        //name为参数
        // settings
        Settings settings = Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2).build();
        // mapping
        XContentBuilder mappingBuilder;
        try {
            XContentBuilder one = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject(index);
            one = one.startObject("properties");
            for (int i = 0; i < name.size(); i++) {
                one = one.startObject(name.get(i)).field("type", "string").field("store", "yes").endObject();
            }
            one = one.startObject(name.get(name.size() - 1)).field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject();
            mappingBuilder = one.endObject().endObject().endObject();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("--------- createIndex 创建 mapping 失败：");
            return false;
        }
        IndicesAdminClient indicesAdminClient = client.admin().indices();
        CreateIndexResponse response = indicesAdminClient.prepareCreate(index)
                .setSettings(settings)
                .addMapping(index, mappingBuilder)
                .get();
        return response.isAcknowledged();
    }

    //外部输入索引名，与map
    //创建索引，获取字符
    public static void save(String index, HashMap<String, String> map, TransportClient client) {
        //检查有没有索引
        try {
            lianjie lianjie = new lianjie();
            if (!lianjie.isExistsIndex(index, client))
                lianjie.createSimpleIndex(index, client);
            lianjie.ess(index, map, client);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String args[]) throws UnknownHostException {

        Settings settings = Settings.builder().put("cluster.name", "cxyu")
                .put("xpack.security.user", "elastic:YangZC*#03").build();
        TransportClient client = new PreBuiltXPackTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));

        lianjie lianjie = new lianjie();
//        //检查有无
//        if( lianjie.isExistsType("spider_weixin","article")){
//            System.out.println("cunzai");
//        }


        //数据保存
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("as", "asdfasd");
        map.put("asd", "asdfasdf");
        map.put("asdsdsdf", "asdfasdf");

        lianjie.save("ter23", map,client);
        //lianjie.createSimpleIndex("new-try");
//        List<String> one=new ArrayList<String>();
//        one.add("as");
//        one.add("asdf");
//        lianjie.createIndex("new-trsdy2",one);
    }

    @Override
    public void run() {
        save_start();
    }
}
