package webmagic_here;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

public class search {

	 static TransportClient client = null;
	public static void main(String args[]) throws IOException{
		Settings settings = Settings.builder().put("cluster.name", "DeepSearch&")
				.put("xpack.security.user", "elastic:YangZC*#03").build();
		 client = new PreBuiltXPackTransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));
		 
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
//		 IndexResponse response = client.prepareIndex("articles_spider_try", "article","1")
//					.setSource(jsonBuilder().startObject().field("content", "kimc123hy").field("title", "asd")
//							.field("url", "cxyu-success,false,success").field("date", df.format(new Date()))
//							.field("tags", "kimc123123hy").field("official_account", "kimchy").endObject())
//					.get();
//			System.out.println(response.status());
		 
		 SearchResponse response = client.prepareSearch("articles_spider_try")
			        .setTypes("title")
			        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
			        
			        .setFrom(0).setSize(60).setExplain(true)
			        .get();
		 response = client.prepareSearch().get();
		 System.out.println(response);
		 client.close();
	}
}
