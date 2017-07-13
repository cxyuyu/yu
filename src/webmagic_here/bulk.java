package webmagic_here;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Sang on 3/22/17.
 */
public class bulk {
	static String[] all_file;

	public static String ReadFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				laststr = laststr + tempString;
				line++;
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
		return laststr;
	}

	public static Map<String, String> get_json(String path) {
		Map<String, String> map = new HashMap<String, String>();
		String sets = ReadFile(path);// 获得json文件的内容
		JSONObject jo = JSONObject.fromObject(sets);// 格式化成json对象
		String officion_content = jo.getString("officion_content");
		String Ner = jo.getString("Ner");
		String id = jo.getString("id");
		String time = jo.getString("time");
		String content = jo.getString("content");
		String title = jo.getString("title");
		String url = jo.getString("url");
		map.put("officion_content", officion_content);
		map.put("Ner", Ner);
		map.put("id", id);
		map.put("time", time);
		map.put("content", content);
		map.put("title", title);
		map.put("url", url);
		return map;
	}

	public static List<Map<String, String>> get_all(List<String> paths) {
		List<Map<String, String>> all_list = new ArrayList<Map<String, String>>();
		//all_file = file_try.getchild(path);
		System.out.println("文件名读取结束");
		for (String file : paths)
			all_list.add(get_json(file));
		System.out.println("获取所有文件内容");
		return all_list;
	}
	
	public static synchronized void save_bulk(TransportClient client,List<Map<String, String>> all_list) throws IOException{
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(int i=0;i<all_list.size();i++){
			Map<String, String> map=all_list.get(i);
		bulkRequest.add(client.prepareIndex("articles_spider", "article")
				.setSource(jsonBuilder().startObject().field("officion_content", map.get("officion_content"))
						.field("Ner", map.get("Ner")).field("id", map.get("id")).field("time", map.get("time"))
						.field("content", map.get("content")).field("title", map.get("title"))
						.field("url", map.get("url")).endObject()));
		}
		BulkResponse bulkResponse = bulkRequest.get();
	}
	
	public static void cun(List<Map<String, String>> all_list) throws IOException, InterruptedException {

		Settings settings = Settings.builder().put("cluster.name", "DeepSearch&")
				.put("xpack.security.user", "elastic:YangZC*#03").build();

		TransportClient client = null;
		try {
			client = new PreBuiltXPackTransportClient(settings).addTransportAddress(
					new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));
		} catch (Exception e) {
			System.out.println("连接问题");
		}
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		System.out.println(new Date());
		
		for(int i=0;i<all_list.size();i++){
			Map<String, String> map=all_list.get(i);
		bulkRequest.add(client.prepareIndex("articles_spider_try", "article")
				.setSource(jsonBuilder().startObject().field("officion_content", map.get("officion_content"))
						.field("Ner", map.get("Ner")).field("id", map.get("id")).field("time", map.get("time"))
						.field("content", map.get("content")).field("title", map.get("title"))
						.field("url", map.get("url")).endObject()));
		}
		System.out.println(new Date());
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
			// process failures by iterating through each bulk response item
			System.out.println("failed");
		}

		client.close();
	}

	public static void main(String[] args) throws InterruptedException  {
//		get_json("/home/cxyu/桌面/code/json/13855.json");
//		String[] a = file_try.getchild("/home/cxyu/桌面/code/json");
//		for (String s : a)
//			System.out.println(s);
//		List<Map<String, String>> all_list=get_all("/home/cxyu/桌面/code/json");
//		Map<String, String> map;
//		for(int i=0;i<all_list.size();i++){
//			map=all_list.get(i);
//			System.out.println(map);
//		}
		
		//获得地址
		String[] a=get.read("conf/conf_data");
		String path=a[2];
		
		bulk_save bs=new bulk_save();
		//由于之前没有保存存入多少数据现在出现问题。
		//解决方法，就放入100万的数据，可能有重复，但是，也就只能这样了。
		//现在的办法是存入67000000以上的数据。
		String[] files=file_try.getchild_gai(path);
		for(String s:files)
			System.out.println(s);
		bs.start();
		for(int i=0;i<20;i++){
			bulk_data bd= new bulk_data(files);
			bd.start();}	
	}
}