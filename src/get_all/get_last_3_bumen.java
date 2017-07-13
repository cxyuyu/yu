package get_all;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import webmagic_here.get_file_my;
import webmagic_here.weixin_new_nerchange;



/*
 * 
 * 手动输入几个板块的链接
 * 
 * 除了办事指南，所有的信息都要
 * 
 * 这里可以得到参数
 * 
 * 页数好像就是最后的page变化
 * http://www.jxgaj.gov.cn/act/tszxlist.aspx?page=2
 * 
 * 创建文件
 * 保存文件的代码都是list<list<String>>
 * 
 * */
public class get_last_3_bumen implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    
    //得到url
    //从原始链接里面获取数据
    public static List<String> get_urls(List<String> url_all) throws IOException, InterruptedException{
    	//http://bbs.clzg.cn/forum-17-1.html
    	//不考虑0的情况了。
    	List<String> URLS=new ArrayList<String>();
    	
    	Document doc;
    	for(String s:url_all){
    	doc = Jsoup.connect(s)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			.timeout(100000)
    			.get();
    	Elements urls=doc.select("#threadlist > div.bm_c > form > table > tbody");
    	for(Element url:urls){
    	String URL=url.select("tr > th > a.s.xst").attr("href");
    	if(URL.length()>10){
    	System.out.println(URL);
    	URLS.add(URL);}
    	}
    	}
    	return URLS;
    }
    
    
    //得到正文
    //外加评论
    public static List<ArrayList<String>> get_content (String url) throws IOException{
    	List<ArrayList<String>> all=new ArrayList<ArrayList<String>>();
    	ArrayList<String> Content =new ArrayList<String>();
    	String content ="";
    	//#postlist
    	Document doc = Jsoup.connect(url)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			.timeout(100000)
    			.get();
    	
    	//只要正文，
		//#pid17131693 > tbody > tr:nth-child(1) > td.plc > div.pct > div > div.t_fsz > table > tbody > tr
    	Elements content_e=doc.select("#postlist > div").get(0)
    			.select("table > tbody > tr:nth-child(1) > td.plc > "
    					+ "div.pct > div > div.t_fsz > table > tbody >"
    					+ " tr > td > font");

		String text="";
		for(Element font :content_e)
		text=text+font.select("font").text();
		//应对咨询投诉
		//#postlist
		if(text.length()<5)
			text=doc.select("#postlist > div > table > tbody > tr:nth-child(1) > td.plc > "
					+ "div.pct > div > div.t_fsz > table > tbody >"
					+ " tr > td").text();
		String title = doc.title();
		Content.add(title);
		Content.add(text);
		all.add(Content);
		Content =new ArrayList<String>();
		System.out.println(title+text);
		
    	//之前数据保存，需要回复信息，现在不需要。
//    	Elements contents=doc.select("#postlist > div");//#postlist
//    	
//    	String title = doc.title();
//    	System.out.println(title);
//    	
//    	for(Element content_y :contents){
//    		Element td=content_y.select("table > tbody > tr:nth-child(1) > td.plc > "
//    				+ "div.pct > div > div.t_fsz > table > tbody > tr > td").first();
//    		String text="";
//    		if(content_y.className().equals("comiis_vrx"))
//    		if(td.select("font").size()==0)
//    			text=td.text();
//    		else{
//    			Elements fonts2 =td.select("td > font");
//    			for(Element font :fonts2)
//    			text=text+font.select("font").text();
//    		}
//    		if(text.length()>0){
//    		Content.add(title+all.size());
//    		Content.add(text);
//    		all.add(Content);
//    		Content =new ArrayList<String>();
//    		}
//
    	return all;
    }
    
    
    //获取单个页面的数据
    @Override
    public void process(Page page) {
        try{
        	
        	//  //h1[@class='entry-title public']/strong/a/text()
        	List<ArrayList<String>> all=get_content(page.getUrl().toString());
        	for(ArrayList<String> a:all){
        		System.out.println(a.get(0)+a.get(1));
        		save(a);}
        	}       
        catch(Exception e){
        	e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
    //特殊为部门在线
    public static List<String> bumen(String url) throws IOException{
    	List<String> urls=new ArrayList<String>();
    	//#subforum_298 > table > tbody
    	Document doc = Jsoup.connect(url)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			.timeout(100000)
    			.get();
    	//#pid13456159 > tbody > tr:nth-child(1) > td.plc
    	//#pid13456159 > tbody > tr:nth-child(1) > td.plc > div.pct > div > div.t_fsz
    	//#postlist
    	//#pid13456159 > tbody > tr:nth-child(1) > td.plc > div.pct > div > div.t_fsz > table > tbody > tr
    	Elements all=doc.select("#subforum_298 > table > tbody > tr");//#postlist
    	for(Element line:all){
    		Elements url_els=line.select("td");
    		for(Element url_el:url_els){
    			String URL=url_el.select("dl > dt > a").attr("href");
    			if(URL.length()>5)
    			urls.add(URL);
    		}
    	}
    	return urls;
    }
    
    //得到部门链接后得到报料，投诉，批评，举报，意见
    public static List<String> bumen2(List<String> url_bumen) throws IOException{
    	List<String> urls=new ArrayList<String>();
    	
    	for(String url:url_bumen){
    	System.out.println(url);
    	Document doc = Jsoup.connect(url)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			.timeout(100000)
    			.get();
    	//#thread_types
    	Elements all=doc.select("#thread_types > li");
    	for(Element li:all){
    		String title = li.select("a").text();
    		title=title.split("")[0]+title.split("")[1];
    		//if(title.equals("报料")||title.equals("投诉")||title.equals("批评")||title.equals("意见"))
    		//为了数据纯净，只要投诉，批评
    		if(title.equals("投诉")||title.equals("批评")||title.equals("拍砖")||title.equals("举报"))
    		{
    			
    			String url_yw=li.select("a").attr("href");
    	
    			urls.add(url_yw);
    		}
    	}
    	}
    	return urls;
    }
    
    //得到想要关键词的链接后，得到页数的链接。
    //页面直接加&page=即可
    //当页面没有多于page时加&page=1即可
    //问题：
    //select为空的怎么处理---通过判断size
    public static List<String> bumen3(List<String> url_bumen) throws IOException{
    	List<String> urls=new ArrayList<String>();
    	
    	for(String url:url_bumen){
    	Document doc = Jsoup.connect(url)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			.timeout(100000)
    			.get();
    	//#thread_types
    	//#fd_page_top > div > label > span
    	//#fd_page_top > div > label > span
    	//获取部门数据
    	int page_s=0;//页面的数字
    	Elements all=doc.select("#fd_page_top > div > label > span");
    	if(all.size()!=0){
    	String page_c=all.get(0).attr("title");
    	page_s=Integer.valueOf(page_c.split(" ")[1]);
    	}
    	else
    	page_s=1;	
    	//得到页面的数字
    	for(int i=1;i<page_s;i++){
    		url=url+"&page="+i;
    		urls.add(url);
    	}
    	System.out.println(page_s);
    	}
    	return urls;
    }
    
    
    public static void main(String[] args)  {
    	//产生url
    	//http://bbs.clzg.cn/forum-17-1.html
    	//先网谈天下的爆料
    	try {
    		List<String> url_all= bumen("http://bbs.clzg.cn/forum-298-1.html");
    		//添加网谈天下，消费维权，
    		url_all.add("http://bbs.clzg.cn/forum-17-1.html");//网谈天下
    		url_all.add("http://bbs.clzg.cn/forum-227-1.html");//咨询投诉
    		url_all.add("http://bbs.clzg.cn/forum-141-1.html");//昆明发展
    		url_all.add("http://bbs.clzg.cn/forum-281-1.html");//消费维权
			url_all=bumen2(url_all);
			url_all=bumen3(url_all);
			List<String> url_all2= new ArrayList<String>();
    		for(String s:url_all)
    			if(s.length()>5)
    			url_all2.add(s);
    			
    			
    		List<String> urls=get_urls(url_all2);
    		String[] URLs=new String[urls.size()];
			for(int i=0;i<urls.size();i++)
				URLs[i]=urls.get(i);//得到所有链接
			//单个链接获取数据
	        Spider.create(new get_last_3_bumen())
	        .addUrl(URLs)
	        .thread(9)
	        .run();
    		
    		
    		
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	//评论里附和的数据，再创造文件-1，-2，-3.。。。。。
    	
    	
    }
    
    
    /*
     * 
     * 
     * 
     * 无关爬虫的代码都写下面
     * 
     * */
    
    
    //保存总
    public static void save(List<String> content){
    	try {
    	String filename=crate_flie("/home/cxyu/Desktop/舆情分析/云南",content.get(0));
    	List<String> name=new ArrayList<String>();
    	List<String> content_2=new ArrayList<String>();
    	content_2.add(content.get(1));
    	//保存,id,作者，时间，标题，内容
    	name.add("content");
    	String contents=testMapToJSON(name,content_2);
    	System.out.println(contents);
		writeFile(contents,filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //产生文件
    public static  String crate_flie(String path,String filename) {
		String destFileName = path+"/"+filename+".json";
		//System.out.println(destFileName);
		File file = new File(destFileName);
		
		try {
			if (file.createNewFile()) {
				System.out.println("创建单个文件" + destFileName + "成功！");
				return destFileName;
			} else {
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
    
    //数据保存
    public static void writeFile(String sets,String filename) throws IOException {
			FileWriter fw;
			fw = new FileWriter(filename, true);
			PrintWriter out = new PrintWriter(fw);
			out.write(sets);
			out.close();
			fw.close();
	}
    
    
    //数据以json的格式保存
    //name保存像id，time，url，一类的树，all为标题的内容
    public static String testMapToJSON(List<String> name,List<String> all) {
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0;i<name.size();i++)
			map.put(name.get(i), all.get(i));
//		map.put("id", id);
//		map.put("title", title);
//		map.put("officion_content", title);
//		map.put("content", content);
//		map.put("time", time);
//		map.put("Ner", Ner);
//		map.put("url", url);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}
    
    
}
