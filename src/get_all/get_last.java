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
 * 这是每个链接的地址，
 * http://www.xf.gov.cn/network/index_467_1.shtml
 * 修改最后一个参数就可以得到所需的网页
 * 
 * 获得投诉信息先
 * 
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
public class get_last implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    
    //筛选符合条件的链接
    public static List<String> sx(List<String> urls){
    	List<String> c=new ArrayList<String>();
    	for(String url:urls)
    	if(url.contains("shtml")&&url.length()>80)
    		c.add(url);
    	return c;
    }
    
    //从一个页面得到参数
    public static List<String> get_pa(String url) throws IOException, InterruptedException{
    	List<String> pas=new ArrayList<String>();
    	
    	Document doc = Jsoup.connect(url)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			.timeout(6000)
    			.get();
    	//body > div:nth-child(5) > table > tbody > tr:nth-child(2) > td > table:nth-child(2) > tbody > tr > td > span
    	String id=doc.select("body > div:nth-child(5) > table > tbody > "
    			+ "tr:nth-child(2) > td > table:nth-child(2) > tbody > tr"
    			+ " > td > span").text();
    	String writer =doc.select("body > div:nth-child(5) > table > tbody"
    			+ " > tr:nth-child(2) > td > table.c14 > tbody > tr:"
    			+ "nth-child(2) > td:nth-child(2)").text();
    	String date = doc.select("body > div:nth-child(5) > table > tbody >"
    			+ " tr:nth-child(2) > td > table.c14 > tbody > tr:nth-child"
    			+ "(2) > td.ct.cgary").text();
    	String title =doc.select("body > div:nth-child(5) > table > tbody > "
    			+ "tr:nth-child(2) > td > table.c14 > tbody > tr:nth-child(3)"
    			+ " > td.ct.cb").text();
    	String content = doc.select("body > div:nth-child(5) > table > tbody >"
    			+ " tr:nth-child(2) > td > table.c14 > tbody > tr:nth-child(4) "
    			+ "> td:nth-child(2) > div").text();
    	pas.add(id.split("：")[1]);
    	pas.add(writer);
    	pas.add(date);
    	pas.add(title);
    	pas.add(content);
    	return pas;
    }
    
    @Override
    public void process(Page page) {
        try{
        	List<String> all=page.getHtml().links().all();
        	all=sx(all);//筛选链接
        	for(String s :all)
        		System.out.println(s);
        	for(String url :all){
        	List<String> one_page=get_pa(url);//得到每个页面的数据数据
        	//保存,id,作者，时间，标题，内容
        	//编号拿来做数据文件的名字，不成功保存就把数据丢弃
        	for(String s:one_page)
        		System.out.println(s);
        	save(one_page);
        	}
        	//Thread.sleep(100000);
        	
        	
        	//get_pa(page.getUrl().toString());
        	//get_pa(page);
        }
        catch(Exception e){
        	e.printStackTrace();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    //先是网上咨询，然后其他
    public static void main(String[] args)  {
    	//产生url
//    	String[] urls=new String[199];
//    	String url_yuan="http://www.xf.gov.cn/network/index_467_";
//    	for(int i=1;i<200;i++)
//    	urls[i-1]=url_yuan+i+".shtml";
    	
    	String[] urls=new String[586];
    	String url_yuan="http://fyr.xf.cn:8080/was5/web/search?page=";
    	String wei="&channelid=36859&searchword=%E7%AE%A1"
    			+ "&keyword=%E7%AE%A1&perpage=10&outlinepage=10";
    	for(int i=1;i<587;i++)
    	urls[i-1]=url_yuan+i+wei;
    	
        Spider.create(new get_last())
        .addUrl(urls)
        .thread(5)
        .run();
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
    	String filename=crate_flie("/home/cxyu/桌面/舆情分析/襄阳",content.get(0));
    	List<String> name=new ArrayList<String>();
    	//保存,id,作者，时间，标题，内容
    	name.add("id");
    	name.add("writer");
    	name.add("date");
    	name.add("date");
    	name.add("content");
    	String contents=testMapToJSON(name,content);
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
