package webmagic_here;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class get {
	public static String[] read(String path) {
		String[] s = new String[file_try.file_lines(path)];
		
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
					s[n] =  texts[0];
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] a=get.read("conf/conf_data");
		for(String s:a)
			System.out.println(s);
	}

}
