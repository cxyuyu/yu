package webmagic_here;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.DATA_CONVERSION;

import cn.edu.fudan.flow.NamedIdentityRecognizerStart;

public class bulk_data extends Thread {
	static int files_size;
	public String[] files_name={};
	static  List<List<Map<String, String>>> data_save=Collections.synchronizedList(new ArrayList<List<Map<String, String>>>());
	public static int jishu = 0;// 记录多少个已经保存，只在此函数进行数据改变

	public bulk_data(String[] files) {
		files_name = files;
		files_size = files.length;
	}

	// 将判断与自减合成一个防止多线程干扰
	public static synchronized int cutself() {
		if (jishu < files_size - 100) {
			jishu = jishu + 100;
			return jishu;
		}
		else{
			return jishu;
		}
	}
	//最后的两万
	public static synchronized int cutself_last() {
		
		int zan=jishu;
		jishu=files_size;
		return zan;
	}
	
	// 得到list
	public void run() {
		while (true)
			try {
				// 链表小于6时，进行保存，不是则sleep，防止内存消耗过大
				if (data_save.size() < 6) {
					// 10000次记录一次，防止计算次数过多，导致性能下降。
					List<String> a = new ArrayList<String>();
					List<Map<String, String>> list;
					int zan=cutself();//获得jishu的值，在这里设置线程锁卡住判断，防止重复获取jishu的值
					if (zan < files_size - 100) {//最后的两万一起运行
						for (int i = 0; i < 100; i++)
							a.add(files_name[zan--]);
						list = bulk.get_all(a);
						// 得到了一万条数据，
						data_save.add(list);
					} else {
						//倒数的一万数据
						for (int i = 0; i < 000; i++)
							a.add(files_name[zan--]);
						list = bulk.get_all(a);
						// 得到了一万条数据，
						data_save.add(list);
						//倒数的一万数据
						
						
						// 多线程会在这里遇到问题,解决---判断让它加个线程锁
						int have = cutself_last();
						System.out.println("have"+have);
						for (int i = have; i < files_size; i++)
							a.add(files_name[i++]);
						list = bulk.get_all(a);
						// 得到最后的数据，
						data_save.add(list);
					}
				} else {
					Thread.sleep(100);
					if (jishu == files_size && data_save.size() == 0)
						break;
				}
			} catch (Exception e) {
				System.out.println(new Date());
				e.printStackTrace();
			}
	}

	public static void main(String[] args) {

	}
}
