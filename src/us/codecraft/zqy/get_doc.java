package us.codecraft.zqy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class get_doc {
	
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
					list.add(lineTxt);
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
	
	public static List<String> get_mythink() throws IOException{
		List<String> list=new ArrayList<String>();
		List<String> urls=read("/home/cxyu/桌面/zqy/urls_real");
		for(String s:urls){
			Document doc = Jsoup.connect(s).get();
		String title = doc.title();
		System.out.println(title);
		}
		
		return list;
	}
	
	
	public static void main(String[] args){
		try {
			get_mythink();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
