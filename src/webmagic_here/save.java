package webmagic_here;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;


public class save extends Thread {
//	public save(TransportClient client) {
//		this.client = client;
//	}

	public void ess(List<String> data,long id) throws IOException {
		//System.out.println("save_start");
		
		IndexResponse response2 = client.prepareIndex("spider_weixin", "article", String.valueOf(id))
				.setSource(jsonBuilder().startObject().field("content", data.get(2)).field("title", data.get(1))
						.field("url", data.get(4)).field("date", data.get(3)).field("tags", data.get(5))
						.field("official_account", data.get(0)).endObject())
				.get();
		//System.out.println("状态" + response2.getResult()+new Date());
		
		
		// shutdown
		// client.close();
	}

	public void run() {
		while(true){
			try {
				long id=getid.addid();
				if (weixin_new_nerchange.all_ner.size() != 0) {
					List<String> data = new ArrayList(weixin_new_nerchange.all_ner.remove(0));
					try{
					webmagic_here.StringToJSON.writeFile(webmagic_here.StringToJSON.testMapToJSON(data.get(0), data.get(1),
							data.get(2), data.get(3), data.get(4), data.get(5),
							String.valueOf(id)),id);
					//数据的存储
					ess(data,id);
					System.out.println("save_sucess"+new Date());
					}
					catch (Exception e) {
						// TODO: handle exception
						//id减一，all_ner恢复，client修复
						getid.cutid();
						weixin_new_nerchange.all_ner.add(data);
						//client_false();
						System.out.println("save_false"+new Date());
						e.printStackTrace();
					}
					} else
					try {
						Thread.sleep(100);
					} catch (InterruptedException ie) {
						
					}
			} catch (Exception e) {
				System.out.println(new Date());
				// TODO: handle exception
				e.printStackTrace();
			}}
	}
//	public static void client_false(){
//		Settings settings = Settings.builder().put("cluster.name", "DeepSearch&")
//				.put("xpack.security.user", "elastic:YangZC*#03").build();
//		try {
//			weixin_new_nerchange.client = new PreBuiltXPackTransportClient(settings)
//					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));
//		} catch (UnknownHostException e) {
//			System.out.println(new Date());
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) throws IOException {

		for (int i = 0; i < 6; i++) {
			List<String> s = new ArrayList<String>();
			for (int iw = 0; iw < 6; iw++)
				s.add("sd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			System.out.println();// new Date()为获取当前系统时间
			s.set(3, df.format(new Date()).toString());
			weixin_new_nerchange.all_ner.add(s);
		}
		System.out.println(weixin_new_nerchange.all_ner);

		Settings settings = Settings.builder().put("cluster.name", "DeepSearch&")
				.put("xpack.security.user", "elastic:YangZC*#03").build();
		TransportClient client = new PreBuiltXPackTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));
		save save_new = new save();
		save_new.run();
		// save_new.trys();
	}

	public void trys() throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		IndexResponse response = client.prepareIndex("articles_spider2", "article")
				.setSource(jsonBuilder().startObject().field("content", "kimc123hy").field("title", "asd")
						.field("url", "cxyu_asd_yes").field("date", df.format(new Date())).field("tags", "kimc123123hy")
						.field("official_account", "kimchy").endObject())
				.get();
		System.out.println(response.status());
	}

	private TransportClient client;
}
