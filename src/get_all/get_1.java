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
 * http://www.jxgaj.gov.cn/act/yjhf_detail.aspx?IGUID=70127&Code=w201704250004&action=1&token=0204
 * onclick="gotonext('70127','w201704250004')"
 * 这里可以得到参数
 * 
 * 页数好像就是最后的page变化
 * http://www.jxgaj.gov.cn/act/tszxlist.aspx?page=2
 * 
 * 创建文件
 * 保存文件的代码都是list<list<String>>
 * 
 * */
public class get_1 implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
    
    //从一个页面得到参数
    public static List<String> get_pa(String url) throws IOException, InterruptedException{
    	List<String> pas=new ArrayList<String>();
    	//Document doc = Jsoup.connect(page.toString()).get();
    	Document doc = Jsoup.connect(url)
    			.userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)")
    			
    			.get();
    	Element wyzx_list_nr=doc.select("div.wyzx_list_nr").first();
    	Elements wyzx_list_nrs=wyzx_list_nr.select("div.wyzx_list_nrxx");
    	for(Element wyzx:wyzx_list_nrs)
    	{
    		String	pa=wyzx.select("div.wyzx_list_nr_nr").first().select("a").first().text();
    		System.out.println(pa);
    		Thread.sleep(100000);
    		pas.add(pa);
    	}
    	
    	return pas;
    }
    
    @Override
    public void process(Page page) {
        try{
        	get_pa(page.getUrl().toString());
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

    public static void main(String[] args)  {
    	//产生url
    	String[] urls=new String[175];
    	String url_yuan="http://www.jxgaj.gov.cn/act/tszxlist.aspx?page=";
    	for(int i=1;i<176;i++)
    	urls[i-1]=url_yuan+i;
    	
        Spider.create(new get_1())
        .addUrl(urls)
       // .thread(5)
        .run();
    	
//    	try {
//    	String filename=crate_flie("/home/cxyu/桌面/舆情分析/嘉兴市公安局","try");
//    	List<String> name=new ArrayList<String>();
//    	for(int i=0;i<6;i++)
//    	name.add("name"+i);
//    	System.out.println(name.size());
//    	List<String> content=new ArrayList<String>();
//    	for(int i=0;i<6;i++)
//    	content.add("name"+i);
//    	String contents=testMapToJSON(name,content);
//    	System.out.println(contents);
//    	
//			writeFile(contents,filename);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    
    /*
     * 
     * 
     * 
     * 无关爬虫的代码都写下面
     * 
     * */
    
    
    
    //产生文件
    public static  String crate_flie(String path,String filename) {
		String destFileName = path+"/"+filename;
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
