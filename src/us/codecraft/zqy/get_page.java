package us.codecraft.zqy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import webmagic_here.file_try;
import webmagic_here.weixin_new_nerchange;

public class get_page {
	// json 的实验
	public void get_json() {
		JSONArray jsonarray;
		JSONObject jsonObj;

		// 读取JSONArray，用下标索引获取
		String array = "[\"11\",\"22\",\"33\"]";
		jsonarray = JSONArray.fromObject(array);
		System.out.println(jsonarray.getString(1)); // 输出为：22

		// 读取JSONObject
		String object = "{\"NO1\":[\"44\",\"55\",\"66\"],\"NO2\":{\"NO1\":\"第一个\"}}";
		jsonObj = JSONObject.fromObject(object);
		System.out.println(jsonObj.get("NO1")); // 输出为：["44","55","66"]

		jsonarray = (JSONArray) (jsonObj.get("NO1"));
		System.out.println(jsonarray.getString(1)); // 输出为：55

		// 用"键"获取值
		jsonObj = (JSONObject) jsonObj.get("NO2");
		System.out.println(jsonObj); // 输出为：{"NO1":"第一个"}

	}

	// 文本处理,将页面中的用户评论进行提取
	public static List<String> get_text(String page) throws IOException, InterruptedException {
		List<String> contents = new ArrayList<String>();
		JSONArray jsonarray;
		JSONObject jsonObj;

		String object = page;// 将文本内容放入
		
		
		jsonObj = JSONObject.fromObject(object);
		
		jsonarray = (JSONArray) (jsonObj.get("comments"));

		comtent ct = new comtent();
		// 尝试将jsonarray转化为list。
		// System.out.println(jsonarray.toList(jsonarray, ct));
		// System.out.println(jsonarray.toli);

		// 现在遇到问题只先将数据转为json格式，之后有空再将其转化为String
		for (int i = 0; i < jsonarray.size(); i++) {
			jsonObj = JSONObject.fromObject(jsonarray.get(i));
			System.out.println(jsonObj.get("content"));
			//jsonarray = (JSONArray) (jsonObj.get("content"));
			contents.add(jsonObj.get("content").toString());
		}
		// jsonObj.get("referenceTime");

		return contents;
	}

	public static List<String> list_add_list(List<String> newlist, List<String> all) {
		for (String s : newlist)
			all.add(s);
		return all;
	}

	public static List<String> read(String path) {
		List<String> urls = new ArrayList<String>();
		// 假设内容小于十万条
		try {
			String encoding = "UTF-8";
			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					urls.add(lineTxt);		
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println(new Date());
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return urls;
	}
	
	public static List<String> get_productId2(String url){
		Document doc;
		List<String> productId = new ArrayList<String>();
		
			try{
			System.out.println(url);
			doc = Jsoup.connect(url).get();
			Elements lots = doc.select("div.p-parameter").select("ul").get(2).select("li");
					//.select("ul.parameter2 p-parameter-list").select("li");//查找第一个a元素
			String content=lots.get(1).text();
			System.out.println(content.split("：")[1]);
			productId.add(content.split("：")[1]);}
			catch(Exception e){
				e.printStackTrace();
			}
		
		return productId;
		
	}
	
	////*[@id="detail"]/div[2]/div[1]/div[1]/ul[3]/li[2]
	public static List<String> get_productId()  {
		Document doc;
		List<String> productId = new ArrayList<String>();
		List<String> urls =read("/home/cxyu/桌面/zqy/urls_real");
		for(String url:urls)
		{
			try{
			System.out.println(url);
			doc = Jsoup.connect(url).get();
			Elements lots = doc.select("div.p-parameter").select("ul").get(2).select("li");
					//.select("ul.parameter2 p-parameter-list").select("li");//查找第一个a元素
			String content=lots.get(1).text();
			System.out.println(content.split("：")[1]);
			productId.add(content.split("：")[1]);}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return productId;
	}

	// 输入商品id，获取商品的评价信息
	public static List<String> get_contents(List<String> productId) {
		List<String> contents = new ArrayList<String>();
		Document doc;
		try {
			// 现在先假定productId（商品名）是已经知道的。
			List<String> urls = new ArrayList<String>();
			for (String s : productId) {
				String url = "https://club.jd.com/comment/productPageComments.action?"
						+ "fetchJSON_comment98vv4573&productId=" + s + "&score=0&sortType=5" + "&pageSize=10";
				urls.add(url);
			}
			// 3984688小米的id
			// 尝试1300的页面大小
			// doc = Jsoup.connect(url + "&page=1300").get();
			// System.out.println(doc.body().text().length());

			// 有没有达到2500的字符串长度是评判页面是否到顶的标准
			for(String url:urls)
			for (int i = 0;; i++) {
				try {
					doc = Jsoup.connect(url + "&page=" + i).get();
					String page = doc.body().text();
					if (page.length() < 2500) {
						System.out.println("  jinru " + page);
						break;
					}
					System.out.println(page.length() + "   " + i);

					List<String> content_new = get_text(page);
					list_add_list(content_new, contents);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	}
	public static void StringBufferDemo(String path,List<String> content) throws IOException{
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        for(String s:content){
            StringBuffer sb=new StringBuffer();
            sb.append(s+"\n");
            out.write(sb.toString().getBytes("utf-8"));
        }        
        out.close();
    }
	public static  String crate_flie(String filename) {
		String path="/home/cxyu/桌面/zqy/content";
		String destFileName = path+"/"+filename;
		File file = new File(destFileName);
		
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return destFileName;
			} else {
				crate_flie(filename+"_have");
				System.out.println("创建单个文件" + destFileName + "失败！");
				return null;
			}
		} catch (IOException e) {
			System.out.println(new Date());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
//			List<String> id=get_productId();
//			List<String> content=get_contents(id);
//			//保存数据
//			StringBufferDemo("/home/cxyu/桌面/zqy/内容",content);
			List<String> urls =read("/home/cxyu/桌面/zqy/urls_real");
			for(String url :urls){
			String filename =url.split("/")[3].split("\\.")[0];
			System.out.println(filename);
			
			
			List<String> id=get_productId2(url);
			List<String> content=get_contents(id);
			String nameall=crate_flie(filename);
			StringBufferDemo(nameall,content);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
