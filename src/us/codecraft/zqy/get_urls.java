package us.codecraft.zqy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class get_urls {
	
	public static void StringBufferDemo(String path,List<String> list) throws IOException{
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,true);        
        for(String s:list){
            StringBuffer sb=new StringBuffer();
            sb.append(s+"\n");
            out.write(sb.toString().getBytes("utf-8"));
        }        
        out.close();
    }
	
	
	public static List<String> read(String path) {
		List<String> list=new ArrayList<String>();
		
		// 假设内容小于十万条
		try {
			String encoding = "UTF-8";
			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int n = 0;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String[] texts = lineTxt.split("/");
					String zan;
					zan =  texts[3];
					zan=zan.split("\\.")[0];
					if(list.size()==0)
						list.add(zan);
					for(int i=0;i<list.size();i++)
					if(list.get(i).equals(zan))
					{
						break;
					}
					else{
						if(i==list.size()-1){
						list.add(zan);
						break;}
					}
						n++;
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
		return list;
	}
	
	
	public static void get_line() {
		Document doc;
		try {
			doc = Jsoup.connect("https://search.jd.com/"
					+ "Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E6%89%8B%E6%9C%BA&page=4").get();
			Elements link = doc.select("a");
			for (Element line : link) {
				String relHref = line.attr("href"); // == "/"
				System.out.println(relHref);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws IOException{
		//get_line();
		List<String> ss=read("/home/cxyu/桌面/zqy/urls_少40条 (复件)");
		for(int i=0;i<ss.size();i++){
			ss.set(i,"https://item.jd.com/"+ss.get(i)+".html");
			//System.out.println(ss.get(i));
		}
		for(String s:ss)
			System.out.println(s);
		StringBufferDemo("/home/cxyu/桌面/zqy/urls_少40条",ss);
	}
}
