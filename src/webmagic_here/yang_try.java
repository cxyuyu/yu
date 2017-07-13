package webmagic_here;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.fudan.flow.NamedIdentityRecognizerStart;
import us.codecraft.webmagic.Page;

public class yang_try {
	@SuppressWarnings("resource")
	static String url = "";

	public static void main(String[] args) {
		try {

			yang_try y = new yang_try();
			Document doc;
			doc = y.get_document();

			y.get_all(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("网页缺少相应参数，为无效网页，不进行保存操作");
			e.printStackTrace();
		}
	}


	public Document get_document() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("url控制台输入：");
		String urlget = sc.nextLine();
		url = urlget;
		System.out.println(url);
		Document doc = Jsoup.connect(url).get();

		return doc;
	}

	public String gettxt(Document doc) {
		String txt = "";
		Elements p = doc.select("div.rich_media_content ").select("p");
		for (Element text : p)
			txt = txt + text.text();
		// System.out.println(txt);
		return txt;
	}

	public static String get_ner(String content){
		 NamedIdentityRecognizerStart ner = new NamedIdentityRecognizerStart();
			String Ner = weixin_new_nerchange
					.change(weixin_new_nerchange.Ner_content("1" + content + "1",ner));
			return Ner;
	}
	
	public static void ess(List<String> data) throws IOException {
		// es链接
		Settings settings = Settings.builder().put("cluster.name", "DeepSearch&")
				.put("xpack.security.user", "elastic:YangZC*#03").build();
		 TransportClient client = new PreBuiltXPackTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("124.127.117.108"), 9300));

		IndexResponse response2 = client.prepareIndex("articles_spider", "article")
				.setSource(jsonBuilder().startObject().field("content", data.get(2)).field("title", data.get(1))
						.field("url", data.get(4)).field("date", data.get(3)).field("tags", data.get(5))
						.field("official_account", data.get(0)).endObject())
				.get();
		System.out.println("状态" + response2.getResult());
		
		
		// shutdown
		 client.close();
	}
	
	
	// http://mp.weixin.qq.com/s?__biz=NzcwMjEwNDgx&idx=1&mid=2650632143&sn=64dbe8d00ae5a7c2d9dcff3767ec8128

	public List<String> get_all(Document doc) throws IOException {

		List<String> data = new ArrayList<String>();
		// 文本内容提取----1
		String officion_content = doc.select("div.rich_media_meta_list").first().select("a").first().text();
		System.out.println(officion_content);
		String content = gettxt(doc);
		System.out.println(content);
		String title = doc.title();
		System.out.println(title);
		if ((title == null && officion_content == null) || content == "" || content == null) {
			System.out.println("网页缺少相应参数，为无效网页，不进行保存操作");
		} else {
			// 文本内容提取---2
			// System.out.println("get_content "+new Date());

			String time = doc.select("div.rich_media_meta_list").first().select("em").first().text();
			System.out.println(time);
			// 放入list中,
			data.add(officion_content);
			data.add(title);
			data.add(content);
			data.add(time);
			data.add(url);
			data.add(get_ner(content));
			ess(data);
		}
		return data;
	}
}
