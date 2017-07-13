package xuejie_code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class get_csv {

	public static List<ArrayList<String>> read_csv(CsvReader r) throws IOException, InterruptedException {
		// 读取首行数据
		r.readHeaders();
		// 数据的保存
		List<ArrayList<String>> read = new ArrayList<ArrayList<String>>();

		// 结果值
		String result = null;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			// 单行数据
			ArrayList<String> line = new ArrayList<String>();
			// 结果值
			ArrayList<String> jieguozhi = new ArrayList<String>();

			// 显示一行的数据
			//System.out.println(r.getRawRecord());
			// 按列读取这条记录的值
			
			
			line.add(time_chuli(r.get(1)));// 时间
			line.add(r.get(2));
			line.add(r.get(5));
			
			for (int i = 7; i < 17; i++) {

				jieguozhi.add(r.get(i));

			}
			result = get_result(jieguozhi);
			line.add(result);
			line.add(r.get(6));
			System.out.println(r.get(6));
			if (result != null)
				read.add(line);
			
		//特殊处理，因为有数据在r为6处有问题，
			if(r.get(5).equals("	"))
				read.remove(read.size()-1);
		}
		System.out.println("jiesu");
		r.close();
		return read;
	}
	public static String time_chuli(String time){
		
		String time_yue=time.split("-")[1];
		//System.out.println(time+time_yue);
		return time_yue;
	}
	
	public static String get_result(ArrayList<String> result) {
		int a = -1;// 0：不合格，2：不判定，1：合格
		String answer[] = { "	不合格项	", "	合格项	", "	不判定项	", "	问题项	" };

		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).equals(answer[2]) && a != 2)
				a = 2;
			if (result.get(i).equals(answer[1]))
				a = 1;
			if (result.get(i).equals(answer[0]) || result.get(i).equals(answer[3])) {
				a = 0;
				break;
			}
		}
		if (a == -1)
			return null;
		return answer[a];
	}

	public static void write(List<ArrayList<String>> read,String path) throws IOException {
		CsvWriter wr = new CsvWriter(path, ',', Charset.forName("GBK"));
		for (int i = 0; i < read.size(); i++) {
			ArrayList<String> a = read.get(i);
			String[] contents = { a.get(0), a.get(1), a.get(2), a.get(3),a.get(4) };
			System.out.println(contents[4]);
			wr.writeRecord(contents);
		}
		wr.close();
	}

	public static void check(CsvReader r) throws IOException {
		r.readRecord();
		r.readRecord();
		// System.out.println(r.getRawRecord());
		System.out.println(r.get(8));
		if ("	合格项	".equals(r.get(8)))
			System.out.println("合格");
	}

	// 检查有哪些数据
	public static List<String> view(CsvReader r) throws IOException {
		List<String> a = new ArrayList<String>();
		// 读取首行数据
		r.readHeaders();
		a.add(r.get(7));
		String result;
		while (r.readRecord()) {
			// 单行数据
			ArrayList<String> line = new ArrayList<String>();
			// 结果值
			ArrayList<String> jieguozhi = new ArrayList<String>();

			// 得出不同值。
			for (int i = 7; i < 17; i++) {
				int ji = 0;
				for (int s = 0; s < a.size(); s++) {
					if (a.get(s).equals(r.get(i))) {
					} else {
						ji++;
					}
					if (ji == a.size())
						a.add(r.get(i));
				}
			}
		}
		r.close();
		return a;
	}

	public static void main(String[] args)  {
		try {
			// 生成CsvReader对象，以，为分隔符，GBK编码方式
			CsvReader r = new CsvReader("/home/cxyu/桌面/xuejie-code/毕业论文数据/数据备份/dataset-2017.csv", ',',
					Charset.forName("GBK"));
			List<ArrayList<String>> all = read_csv(r);
			// 数据保存
			for(ArrayList<String> a:all)
			System.out.println(a);
			 write(all,"/home/cxyu/桌面/xuejie-code/毕业论文数据/2017_clean.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
