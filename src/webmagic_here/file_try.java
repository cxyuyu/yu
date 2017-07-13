package webmagic_here;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class file_try {
	public static String[] getchild(String path) {
		File f = new File(path);
		File[] fList = f.listFiles();
		String[] flist = new String[fList.length];
		for (int i = 0; i < fList.length; i++)
			flist[i] = fList[i].getPath();
		return flist;
	}
	public static String[] getchild_gai(String path) {
		File f = new File(path);
		//暂时修改，大于67000000的进行保存
		File[] fList = f.listFiles();
		List<String> fs=new ArrayList<String>();
		
		for (int i = 0; i < fList.length; i++){
			String filename=fList[i].getName();
			String[] names=filename.split("\\.");
			  Integer name = new Integer(names[0]);
		       int name2=name.intValue();
			if(name2>67000000)
				fs.add(fList[i].getPath());}
		String[] flist = new String[fs.size()];
		for(int i=0;i<fs.size();i++)
			flist[i]=fs.get(i);
		return flist;
	}

	public static String[] getMergeArray(String[] a, String[] b) {
		String[] c = new String[a.length + b.length];
		for (int i = 0; i < a.length; i++)
			c[i] = a[i];
		for (int i = 0; i < b.length; i++)
			c[a.length + i] = b[i];
		return c;
	}

	public static int file_lines(String path) {
		try {
			File f = new File(path);
			InputStream input = new FileInputStream(f);
			BufferedReader b = new BufferedReader(new InputStreamReader(input));
			String value = b.readLine();
			int count = 0;
			if (value != null)
				while (value != null) {
					count++;
					value = b.readLine();
				}
			b.close();
			input.close();
			return count;
		} catch (IOException e) {
			System.out.println(new Date());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		// file_try ft=new file_try();
		// String[] f=ft.getchild("/home/cxyu");
		// for(int i=0;i<f.length;i++)
		// System.out.println(f[i]);

		String[] file = file_try.getchild("/home/cxyu/桌面/code/lines");
		
		String[] s = {};
		for (String lines : file)
			System.out.println(lines);
//			s=file_try.getMergeArray(s, weixin_new_nerchange.read(lines));
//		System.out.println(s.length);
	}
}
