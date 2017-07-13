package webmagic_here;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

public class new_try implements PageProcessor {
	
		private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

		@Override
		public void process(Page page) {
			System.out.println(Thread.currentThread().getName() + "正在执行。。。");
			System.out.println("执行");
			// url筛选
			//page.addTargetRequests(page.getHtml().links().all());
			//System.out.println(page.getHtml().links().all());

			// 文本内容提取----1
			String officion_content = page.getHtml().xpath("//*[@id='img-content']/div[1]/span/text()").toString();
			String content = gettxt(page);
			String title = page.getHtml().xpath("//title/text()").toString();
						
		}

		

	

	

		public String gettxt(Page page) {
			String txt = "";
			Document doc = Jsoup.parse(page.getHtml().toString());
			Elements p = doc.select("div.rich_media_content ").select("p");
			for (Element text : p)
				txt = txt + text.text();
			//System.out.println(txt);
			return txt;
		}

		@Override
		public Site getSite() {
			// TODO Auto-generated method stub
			return site;
		}
		

		public static String[] read(String path) {
			String[] s = new String[file_try.file_lines(path)];
			String url = "http://mp.weixin.qq.com/s?__biz=";
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
						String[] texts = lineTxt.split("\t");
						s[n] = url + texts[0];
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
			return s;
		}
		
		// 有静态值
		static List<List> all = Collections.synchronizedList(new ArrayList<List>());
		static List<List> all_ner = Collections.synchronizedList(new ArrayList<List>());
		
		static int bug = 0;
		// json数据文件

		// es
		static String ip;
		static String fliead;
		static String linead;
		
		public static void main(String[] args)  {
			//ip以及文件地址
			String[] a=get.read("conf/conf_data");
			ip=a[1];
			fliead=a[2];
			linead=a[3];
			// urls文件
			String[] file = file_try.getchild(linead);
			String[] s = {};
			for (String lines : file)
				s = file_try.getMergeArray(s, weixin_new_nerchange.read(lines));
			
			String[] s1=new String[13];
			String[] s2=new String[13];
			for(int i=0;i<13;i++)
			{s1[i] = s[i];
			 s2[i] = s[i+13];
			}

			// 下载，提取线程
			
			Spider.create(new new_try())
					.addUrl("http://mp.weixin.qq.com/s?__biz="
							+ "NzcwMjEwNDgx&idx=1&mid=2650632143&sn=64dbe8d00ae5a7c2d9dcff3767ec8128")
					.addUrl(s1).setScheduler(new RedisScheduler(ip)).thread(2).run();
			System.out.println("end");
		}







	}

	

