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

public class change_csv {

	// 某地所有，以一年算
	public static Map<String, Integer> get_mdsy(String path) throws IOException {
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		String result = null;
		// 地点
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key;
		int value;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			if (!r.equals(""))// 有一些特殊情况
				if (map.size() == 0) {
					key = r.get(1);
					value = 1;
					map.put(key, value);
				} else {
					Iterator<String> iter = map.keySet().iterator();
					int ji = 0;
					while (iter.hasNext()) {
						key = iter.next();
						if (r.get(1).equals(key)) {
							map.put(key, map.get(key) + 1);
							break;
						} else {
							ji++;
							if (ji == map.size()) {
								key = r.get(1);
								value = 1;
								map.put(key, value);
								break;
							}
						}
					}
				}
		}

		return map;
	}
	//得到四年中都升高的
	public static List<String> get_4(String[] paths) throws IOException {
		CsvReader r1 = new CsvReader(paths[0], ',', Charset.forName("GBK"));
		CsvReader r2 = new CsvReader(paths[1], ',', Charset.forName("GBK"));
		CsvReader r3 = new CsvReader(paths[2], ',', Charset.forName("GBK"));
		// 结果值
		List<String> result =new ArrayList<String>();
		//三个链条
		List<String> h1 =new ArrayList<String>();
		List<String> h2 =new ArrayList<String>();
		List<String> h1_2 =new ArrayList<String>();//h2在h1里面有的数据
		List<String> h3 =new ArrayList<String>();
		List<String> h1_2_3 =new ArrayList<String>();//基于h1_2的数据，h3在里面有的数据
		// 逐条读取记录，直至读完
		while (r1.readRecord()) {
			h1.add(r1.get(0));
		}
		while (r2.readRecord()) {
			h2.add(r2.get(0));
		}
		while (r3.readRecord()) {
			h3.add(r3.get(0));
		}
		for(String s:h1)
			for(String ss:h2)
				if(s.equals(ss))
					h1_2.add(s);
		//相同的就添加
		for(String s:h1_2)
			for(String ss:h3)
				if(s.equals(ss))
					h1_2_3.add(s);
		return h1_2_3;
	}
	
	public static List<String> get_3(String[] paths) throws IOException {
		CsvReader r1 = new CsvReader(paths[0], ',', Charset.forName("GBK"));
		CsvReader r2 = new CsvReader(paths[1], ',', Charset.forName("GBK"));
		// 结果值
		List<String> result =new ArrayList<String>();
		//三个链条
		List<String> h1 =new ArrayList<String>();
		List<String> h2 =new ArrayList<String>();
		List<String> h1_2 =new ArrayList<String>();//h2在h1里面有的数据
		// 逐条读取记录，直至读完
		while (r1.readRecord()) {
			h1.add(r1.get(0));
		}
		while (r2.readRecord()) {
			h2.add(r2.get(0));
		}
		
		for(String s:h1)
			for(String ss:h2)
				if(s.equals(ss))
					h1_2.add(s);
		
		return h1_2;
	}
	
	
	// 得到某地某种所有
	public static Map<String, Integer> get_mdmzbhg(String path) throws IOException {
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		String result = null;
		// 地点
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key;
		int value;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			// key:福建,婴幼儿配方食品
			if (map.size() == 0) {
				key = r.get(1) + "," + r.get(2);
				value = 1;
				map.put(key, value);
			} else {
				Iterator<String> iter = map.keySet().iterator();
				int ji = 0;
				while (iter.hasNext()) {
					key = iter.next();
					// System.out.println(key);
					String[] keys = key.split(",");
					// 获得地点，种类，都有的情况
					if (r.get(1).equals(keys[0]) && r.get(2).equals(keys[1])) {
						if (r.get(3).equals("不判定项") || r.get(3).equals("问题项"))
							map.put(key, map.get(key) + 1);
						else
							break;
						break;
					} else {
						ji++;
						if (ji == map.size()) {
							// System.out.println(ji);
							key = r.get(1) + "," + r.get(2);
							value = 0;// 刚开始记为0
							map.put(key, value);
							break;
						}
					}
				}
			}
		}

		return map;
	}

	// 某地某种不合格项的数量。
	public static Map<String, Integer> get_mdmzsy(String path) throws IOException {
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		String result = null;
		// 地点
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key;
		int value;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			// key:福建,婴幼儿配方食品
			if (map.size() == 0) {
				key = r.get(1) + "," + r.get(2);
				value = 1;
				map.put(key, value);
			} else {
				Iterator<String> iter = map.keySet().iterator();
				int ji = 0;
				while (iter.hasNext()) {
					key = iter.next();
					// System.out.println(key);
					String[] keys = key.split(",");
					if (r.get(1).equals(keys[0]) && r.get(2).equals(keys[1])) {
						map.put(key, map.get(key) + 1);
						break;
					} else {
						ji++;
						if (ji == map.size()) {
							// System.out.println(ji);
							key = r.get(1) + "," + r.get(2);
							value = 1;
							map.put(key, value);
							break;
						}
					}
				}
			}
		}

		return map;
	}

	// 获取每月某地某类的数量
	public static Map<String, Integer> get_mymdmzsy(String path) throws IOException {
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		String result = null;
		// 地点
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key;
		int value;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			// key:(月份)09,福建,婴幼儿配方食品
			if (map.size() == 0) {
				key = r.get(0) + "," + r.get(1) + "," + r.get(2);
				value = 1;
				map.put(key, value);
			} else {

				Iterator<String> iter = map.keySet().iterator();
				int ji = 0;
				while (iter.hasNext()) {
					key = iter.next();
					// System.out.println(key);
					String[] keys = key.split(",");
					// 获取是不是这一个月的。
					if (r.get(0).equals(keys[0])) {
						if (r.get(1).equals(keys[1]) && r.get(2).equals(keys[2])) {
							map.put(key, map.get(key) + 1);
							break;
						} else {
							// 没有同地同类的情况
							ji++;
							if (ji == map.size()) {
								// System.out.println(ji);
								key = r.get(0) + "," + r.get(1) + "," + r.get(2);
								value = 1;
								map.put(key, value);
								break;
							}
						}

					} else {
						// 没有这个月份的情况
						ji++;
						if (ji == map.size()) {
							// System.out.println(ji);
							key = r.get(0) + "," + r.get(1) + "," + r.get(2);
							value = 1;
							map.put(key, value);
							break;
						}
					}
				}
			}
		}

		return map;
	}

	public static Map<String, Integer> get_mybhg(String path) throws IOException {
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		String result = null;
		// 地点
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key;
		int value;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			// key:(月份)09,福建,婴幼儿配方食品
			if (map.size() == 0) {
				key = r.get(0) + "," + r.get(1) + "," + r.get(2);
				value = 1;
				map.put(key, value);
			} else {

				Iterator<String> iter = map.keySet().iterator();
				int ji = 0;
				while (iter.hasNext()) {
					key = iter.next();
					// System.out.println(key);
					String[] keys = key.split(",");
					// 获取是不是这一个月的。
					if (r.get(0).equals(keys[0])) {
						if (r.get(1).equals(keys[1]) && r.get(2).equals(keys[2])) {
							if (r.get(3).equals("不判定项") || r.get(3).equals("问题项"))
								//得到一个不包含指标的行
								{
								String[] zhibiao=key.split(",");
								for(int i=3;i<zhibiao.length;i++){
								if(r.get(4).equals(zhibiao[i])){
									System.out.println(r.get(4));
									map.put(key, map.get(key) + 1);
									break;
								}
								if(i==zhibiao.length){
									System.out.println(r.get(4));
									map.put(key+ "," +r.get(4), map.get(key) + 1);
								
								}
								}
								}
								else
								break;
							break;
						} else {
							// 没有同地同类的情况
							ji++;
							if (ji == map.size()) {
								// System.out.println(ji);
								key = r.get(0) + "," + r.get(1) + "," + r.get(2);
								value = 0;
								map.put(key+ "," +r.get(4), value);
								break;
							}
						}

					} else {
						// 没有这个月份的情况
						ji++;
						if (ji == map.size()) {
							// System.out.println(ji);
							key = r.get(0) + "," + r.get(1) + "," + r.get(2);
							value = 0;
							map.put(key+ "," +r.get(4), value);
							break;
						}
					}
				}
			}
		}

		return map;
	}

	public static Map<String, Double> get_value(Map<String, Integer> loca, Map<String, Integer> zi) {
		Map<String, Double> map = new HashMap<String, Double>();
		String key_loca, key_zi;
		double value;
		Iterator<String> iter = loca.keySet().iterator();
		while (iter.hasNext()) {
			key_loca = iter.next();
			Iterator<String> iter_zi = zi.keySet().iterator();
			while (iter_zi.hasNext()) {
				key_zi = iter_zi.next();
				String[] key_zis = key_zi.split(",");
				if (key_zis[0].equals(key_loca)) {
					// System.out.println(key_zi+" "+key_loca);
					// System.out.println(zi.get(key_zi)+"
					// "+loca.get(key_loca));
					value = (double) zi.get(key_zi) / (double) loca.get(key_loca);
					// value=(double)
					// (Math.round(zi.get(key_zi)/loca.get(key_loca))/10000.0);
					map.put(key_zi, value);
				}
			}
		}
		return map;
	}

	// 读取月份的文件，获取某月某地某类有问题的list,不包括新出现的（所以去除零）。
	public static List<String> get_new_error(String path) throws IOException {
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		List<String> zuihou = new ArrayList<String>();
		// 地点
		Map<String, Integer> map = new HashMap<String, Integer>();
		String key;
		int value;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			// 不合格率不为零的情况下
			if (!r.get(1).equals("0.0")){
				//指标放到最后去，之后再拿出来
				String[] rs=r.get(0).split(",");
				String zhibiao =rs[3];
				String qian=rs[0]+rs[1]+rs[2];
				System.out.println(qian);
				zuihou.add(qian + "/" + r.get(1)+"/"+zhibiao);
			}
		}

		return zuihou;
	}

	public static List<String> list_change(List<String> a) {
		List<String> newer = new ArrayList<String>();
		for (String s : a)
			newer.add(s.split("/")[0]);
		return newer;
	}

	public static List<String> get_haven(String old_path, String new_path) throws IOException {
		List<String> old = get_new_error(old_path);
		List<String> newer = get_new_error(new_path);
		// 结果值
		List<String> old2, newer2;
		List<String> reslut = new ArrayList<String>();
		old2 = list_change(old);
		newer2 = list_change(newer);
		for (int i = 0; i < newer2.size(); i++)
			for (int a = 0; a < old2.size(); a++) {
				if (newer2.get(i).equals(old2.get(a)))
					break;
				if (a == old2.size() - 1)
					if (!newer2.get(i).equals(old2.get(a)))
						reslut.add(newer.get(i));
			}
		return reslut;
	}

	public static List<String> get_higher(String old_path, String new_path) throws IOException {
		List<String> old = get_new_error(old_path);
		List<String> newer = get_new_error(new_path);
		// 结果值
		List<String> old2, newer2;
		List<String> reslut = new ArrayList<String>();
		old2 = list_change(old);
		newer2 = list_change(newer);
		for (int i = 0; i < newer2.size(); i++)
			for (int a = 0; a < old2.size(); a++) {
				if (newer2.get(i).equals(old2.get(a))) {
					// 如果相同，进行数值比较。
					double new_gl = Double.valueOf(newer.get(i).split("/")[1]);
					double old_gl = Double.valueOf(old.get(a).split("/")[1]);
					System.out.println(new_gl);
					System.out.println(old_gl);
					if (new_gl > old_gl)
						reslut.add(newer.get(i));
					System.out.println(newer.get(i));
				}
				else
					if(newer2.get(i).length()==(old2.get(a)).length())
						System.out.println(newer2.get(i)+(old2.get(a)));
			}
		return reslut;
	}

	public static Map<String, Double> get_mymdsy(String path) throws IOException {
		double a = 0;
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		Map<String, Double> map = new HashMap<String, Double>();
		// 地点
		String key;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			if (map.size() == 0)
				map.put(r.get(0) + "-" + r.get(1), 1.0);
			else {
				Iterator<String> iter = map.keySet().iterator();
				int ji = 0;
				while (iter.hasNext()) {
					key = iter.next();
					ji++;
					if (key.equals(r.get(0) + "-" + r.get(1))) {
						map.put(key, map.get(key) + 1);
						break;
					}
					if (ji == map.size())
						map.put(r.get(0) + "-" + r.get(1), 1.0);
				}
			}
		}
		return map;
	}

	
	public static Map<String, Double> wenben_xiugai(String path) throws IOException {
		double a = 0;
		CsvReader r = new CsvReader(path, ',', Charset.forName("GBK"));
		// 结果值
		Map<String, Double> map = new HashMap<String, Double>();
		// 地点
		String key;
		// 逐条读取记录，直至读完
		while (r.readRecord()) {
			r.get(0);
			if(r.get(0).split(",").length>3)
			map.put(r.get(0), Double.valueOf(r.get(1)));
			if(r.get(0).split(",").length>4)
				System.out.println(r.get(0));
		}
		return map;
	}
	
	
	public static Map<String, Double> get_value_lve(Map<String, Double> mysy, Map<String, Integer> zi) {
		//zi为某地某月某类的不合格数量，mysy为某地某月所有的数量
		//zi为	02,贵州,蔬菜制品
		//mysy为   02-贵州
		Map<String, Double> map = new HashMap<String, Double>();
		String key_zi;
		double value;
		Iterator<String> iter = zi.keySet().iterator();
		while (iter.hasNext()) {
			//循环zi
			key_zi= iter.next();
			String[] keys=key_zi.split(",");
			String new_key=keys[0]+"-"+keys[1];
			
			double result=0;
			
			//循环mysy得到一个月的所有结果
			String key_jie="";
			Iterator<String> iter_jiegou  = mysy.keySet().iterator();
			while (iter_jiegou.hasNext()) {
				//循环zi
				key_jie= iter_jiegou.next();
				if(key_jie.equals(new_key)){
					result=mysy.get(key_jie);
					break;
				}
			}		
			value = (double) zi.get(key_zi) / result;
			map.put(key_zi, value);
		}
		return map;
	}

	
	//文本修改读取
	public static List<List<String>> read_change(String path,String nian) throws IOException {
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
			zis.add(1, nian);
			zis.add(r.get(1));
			//将其list化了。
			result.add(zis);
		}
		return result;
	}
	
	
	
	
	
	public static void write(Map<String, Double> read, String path) throws IOException {
		CsvWriter wr = new CsvWriter(path, ',', Charset.forName("GBK"));
		Iterator<String> iter = read.keySet().iterator();
		String key_zi_all;
		double value;
		while (iter.hasNext()) {
			key_zi_all = iter.next();
			value = read.get(key_zi_all);
			String[] contents = { key_zi_all, "" + value };
			wr.writeRecord(contents);
		}

		wr.close();
	}

	public static void write_list(List<String> read, String path) throws IOException {
		CsvWriter wr = new CsvWriter(path, ',', Charset.forName("GBK"));
		for (String s : read) {
			String[] contents = s.split("/");
			wr.writeRecord(contents);
		}
		wr.close();
	}
	public static void write_list_list(List<List<String>> read, String path) throws IOException {
		CsvWriter wr = new CsvWriter(path, ',', Charset.forName("GBK"));
		for (List<String> s : read) {
			System.out.println(s);
			String[] contents = new String[6];
			for(int i=0;i<6;i++)
				contents[i]=s.get(i);
			wr.writeRecord(contents);
		}
		wr.close();
	}
	
	
	public static void main(String[] args) {

		try {
			// Map<String, Integer>
			// loca=get_mdsy("/home/cxyu/桌面/xuejie-code/毕业论文数据/2016_clean.csv");

			// Map<String, Integer> zi_all=
			// get_mdmzsy("/home/cxyu/桌面/xuejie-code/毕业论文数据/2017_clean.csv");
			// System.out.println(zi_all);
			// //得到一个地方一年某种的所有数量
			// Map<String, Integer> zi=
			// get_mdmzbhg("/home/cxyu/桌面/xuejie-code/毕业论文数据/2017_clean.csv");
			// System.out.println(zi);
			// //得到一个地方一年某种的不合格的所有数量
			// Map<String, Double> map=get_value_lve(zi_all,zi);
			// System.out.println(map);
			// //得到一个地方一年某种的不合格的概率
			// write(map,"/home/cxyu/桌面/xuejie-code/毕业论文数据/2017_nian.csv");
			// //写入文件

//			Map<String, Integer> zi_yue = get_mymdmzsy("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014_clean.csv");
//			System.out.println(zi_yue);
//			// 得到一个地方一年一月的所有数量6
//			Map<String, Integer> zi_yue_bhg = get_mybhg("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014_clean.csv");
//			System.out.println(zi_yue_bhg);
//			// 得到一个地方一年一月的不合格数量
//			Map<String, Double> map = get_value_lve(zi_yue, zi_yue_bhg);
//			System.out.println(map);
//			// 得到一个地方一年某种的不合格的概率
//			write(map, "/home/cxyu/桌面/xuejie-code/毕业论文数据/2014-get.csv");
//			// 写入文件

//			Map<String, Double> zi_yue = get_mymdsy("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014_clean.csv");
//			System.out.println(zi_yue);
//			//得到一月的数量
//			Map<String, Integer> zi_yue_bhg = get_mybhg("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014_clean.csv");
//			//System.out.println(zi_yue_bhg);
//			Iterator<String> iter = zi_yue_bhg.keySet().iterator();
//			String key_zi_all;
//			while (iter.hasNext()) {
//				key_zi_all = iter.next();
//				System.out.println(key_zi_all);
//			}
//			// 得到一个地方一年一月的不合格数量
//			Map<String, Double> map = get_value_lve(zi_yue, zi_yue_bhg);
//			//System.out.println(map);
//			write(map, "/home/cxyu/桌面/xuejie-code/毕业论文数据/2014-get.csv");
//			// 写入文件
			
//			List<List<String>> list2017=read_change("/home/cxyu/桌面/xuejie-code/毕业论文数据/2017-get.csv", "2017");
//			List<List<String>> list2016=read_change("/home/cxyu/桌面/xuejie-code/毕业论文数据/2016-get.csv", "2016");
//			List<List<String>> list2015=read_change("/home/cxyu/桌面/xuejie-code/毕业论文数据/2015-get.csv", "2015");
//			List<List<String>> list2014=read_change("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014-get.csv", "2014");
//			//将所有的值加在listall里
//			List<List<String>> listall=new ArrayList<List<String>>();
//			List<List<String>> list_yue=new ArrayList<List<String>>();
//			for(List<String> a:list2017)
//				listall.add(a);
//			for(List<String> a:list2016)
//				listall.add(a);
//			for(List<String> a:list2015)
//				listall.add(a);
//			for(List<String> a:list2014)
//				listall.add(a);
//			
//			for(List<String> a:listall)
//				if(a.get(0).equals("01"))
//					list_yue.add(a);
//			//System.out.println(list_yue);
//			
//			//这步骤还没写
//			write_list_list(list_yue, "/home/cxyu/桌面/xuejie-code/毕业论文数据/yue/01.csv");
//			//write_list_list(listall, "/home/cxyu/桌面/xuejie-code/毕业论文数据/all.csv");

			
//			 List<String> a =
//			 get_haven("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014-get.csv",
//			 "/home/cxyu/桌面/xuejie-code/毕业论文数据/2015-get.csv");
//			 for (String s : a)
//			 System.out.println(s);
//			 write_list(a,"/home/cxyu/桌面/xuejie-code/毕业论文数据/new-2014-2015.csv");

			 List<String> a =
			 get_higher("/home/cxyu/桌面/xuejie-code/毕业论文数据/2015-get.csv",
			 "/home/cxyu/桌面/xuejie-code/毕业论文数据/2016-get.csv");
			 System.out.println("changshi");
			 for (String s : a)
			 System.out.println(s);
			 System.out.println("changshi");
			 //write_list(a,"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2015-2016.csv");

			
//			 List<String> a =
//					 get_higher("/home/cxyu/桌面/xuejie-code/毕业论文数据/2014-get.csv",
//					 "/home/cxyu/桌面/xuejie-code/毕业论文数据/2015-get.csv");
//			 List<String> b =
//					 get_higher("/home/cxyu/桌面/xuejie-code/毕业论文数据/2015-get.csv",
//					 "/home/cxyu/桌面/xuejie-code/毕业论文数据/2016-get.csv");
//			 List<String> c =
//					 get_higher("/home/cxyu/桌面/xuejie-code/毕业论文数据/2015-get.csv",
//					 "/home/cxyu/桌面/xuejie-code/毕业论文数据/2016-get.csv");
//			System.out.println(a);
//			 for (String s : a)
//					 System.out.println(s);
//					 write_list(a,"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2014-2015.csv");
		
//			String[] path={"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2014-2015.csv",
//					"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2015-2016.csv",
//					"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2016-2017.csv"};
//			List<String> a=get_4(path);
//			System.out.println(a);
			//四年的都增长的没有，2017数据过少。
			
//			String[] path={"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2014-2015.csv",
//					"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-2015-2016.csv"};
//			List<String> a=get_3(path);
//			System.out.println(a);
//			 write_list(a,"/home/cxyu/桌面/xuejie-code/毕业论文数据/higher-3.csv");
			
			//处理一些问题，缺少字符串的进行删减
//			Map<String, Double> map = wenben_xiugai("/home/cxyu/桌面/xuejie-code/毕业论文数据/2017-get.csv");
//			//System.out.println(map);
//			write(map, "/home/cxyu/桌面/xuejie-code/毕业论文数据/2017-get.csv");
//			// 写入文件
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
