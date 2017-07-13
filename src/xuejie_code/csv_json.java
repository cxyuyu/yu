package xuejie_code;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import webmagic_here.get_file_my;

public class csv_json {
	
	//2014-get
	public static List<List<String>> read_change(String path) throws IOException {
		//读取get文件
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		List<List<String>> result = new ArrayList<List<String>>();
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			//"03,江苏,糖果制品",4.89077274209325E-4
			List<String> zis=new ArrayList<String>();
			String[] a=r.get(0).split(",");
			for(String s:a)
				zis.add(s);
			zis.add(r.get(1));
			//将其list化了。
			result.add(zis);
		}
		return result;
	}
	
	//2014-nian
		public static List<List<String>> read_nian(String path) throws IOException {
			//读取get文件
			CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
			// 结果值
			List<List<String>> result = new ArrayList<List<String>>();
			// 逐条读取记录，直至读完
			while (r.readRecord()) {
				//"03,江苏,糖果制品",4.89077274209325E-4
				List<String> zis=new ArrayList<String>();
				String[] a=r.get(0).split(",");
				for(String s:a)
					zis.add(s);
				//zis.add(r.get(1));
				//将其list化了。
				result.add(zis);
			}
			return result;
		}
	
	
	//2017-clean
	public static List<List<String>> read_clean(String path) throws IOException {
		//读取get文件
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		List<List<String>> result = new ArrayList<List<String>>();
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			//"03,江苏,糖果制品",4.89077274209325E-4
			List<String> zis=new ArrayList<String>();
			for(int i=0;;i++)
				if(r.get(i).length()>1)
					zis.add(r.get(i));
				else
					break;
//			zis.add(r.get(0));
//			zis.add(r.get(1));
//			zis.add(r.get(2));
//			zis.add(r.get(3));
//			zis.add(r.get(4));
			//将其list化了。
			result.add(zis);
		}
		return result;
	}
	
	
	public static String testListToJSON(List<List<String>> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		//System.out.println(jsonArray.toString());
		// prints ["first","second"]
		return jsonArray.toString();
	}
	
	public static void writeFile(String sets,String fliename) throws IOException {
		
			FileWriter fw;
			fw = new FileWriter(fliename, true);
			PrintWriter out = new PrintWriter(fw);
			out.write(sets);
			out.close();
			fw.close();
			//System.out.print("fliename" + fliename+new Date());
		
	}
	
	public static void main(String[] args){
		try {
			List<List<String>> list2017=read_clean("/home/cxyu/桌面/xuejie-code/毕业论文数据/new-2016-2017.csv");
		
			String ss=testListToJSON(list2017);
			System.out.println(ss);
			writeFile(ss, "/home/cxyu/桌面/xuejie-code/毕业论文数据/new-2016-2017.json");
			
			
			//String a=testListToJSON();
			//System.out.println(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
