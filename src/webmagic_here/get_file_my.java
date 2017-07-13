package webmagic_here;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class get_file_my extends Thread{
	public static synchronized String crate3() {
		System.out.println("here");
		String[] flies = file_try.getchild(weixin_new_nerchange.fliead);
		
		for (int i = 0; i < flies.length; i++) {
			String split[];
			// 得到文件名
			split = flies[i].split("/");
			// flies[i]=split[split.length-1];
			// 得到文件名前部
			split = split[split.length - 1].split("\\.");
			flies[i] = split[0];
		}
		
		int big = 1;
		if(flies.length==0)
		big=-1;
		if(flies.length==1)
		big=0;
		if (flies.length > 1)
			for (int i = 0; i < flies.length; i++)
				if (big < Integer.parseInt(flies[i]))
					big=Integer.parseInt(flies[i]);
		big++;
		String filename=big+".json";
		System.out.println(filename);
		
		String destFileName = weixin_new_nerchange.fliead+filename;
		System.out.println(destFileName);
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

	public void run() {
		   while(true)
			   try {
				   StringToJSON.writeFile("in",1);
				   Thread.sleep(1000);
				//   play();
			   } catch (Exception e) {
				   System.out.println(new Date());
					// TODO: handle exception
				}
 }
	public static  String crate_flie(long id) {
		//System.out.println("here");
		
		long big =id;
		String filename=big+".json";
		//System.out.println(filename);
		
		String destFileName = weixin_new_nerchange.fliead+"/"+filename;
		//System.out.println(destFileName);
		File file = new File(destFileName);
		
		try {
			if (file.createNewFile()) {
				//System.out.println("创建单个文件" + destFileName + "成功！");
				return destFileName;
			} else {
				//System.out.println("创建单个文件" + destFileName + "失败！");
				return null;
			}
		} catch (IOException e) {
			System.out.println(new Date());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static final ReentrantLock lock = new ReentrantLock();
	public static   String[] play() {
		
		String[] flist={};
		
		//上锁
        lock.lock();
        try {
            //保证线程安全操作代码	
    		i.add("");
    		System.out.println("yes");
        } catch(Exception e) {
        	System.out.println(new Date());
        } finally {
            lock.unlock();//释放锁
        }
        return flist;
	}
	
	public static List<String> i;
	public static void main(String[] args) throws IOException {
		get_file_my get=new get_file_my();
		//for(int i=0;i<1;i++)
		//get.start();
		//crate_flie(1);
		crate_flie(1);
	}	
}
