package baidubaike;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxyu on 17-6-23.
 */
public class change {

    public static List<String> read(String path){
        List<String> words=new ArrayList<String>();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                words.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }


            //去掉空格
    public static String removesomething(String word){

        String[] s=word.split(" ");
        String sd="";
        for(String a:s) {
                sd+=a;
        }
        //System.out.println(sd);
        return sd;
    }


    //输入一行，然后进行切割，去空
    public static String qukong(String line){
        String newline="";
        String words[]=line.split("  ");
        for(String word:words){
            word=removesomething(word);
            newline+=word+"\t";
        }
        //System.out.println(newline);
        return newline;
    }

    public static void appendMethodB(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getpath(String path){
        List<String> name=new ArrayList<String>();
        File file=new File(path);
        File[] files=file.listFiles();
        for(File one:files){
            name.add(one.getPath());
            //System.out.println(one);
        }
        return name;
    }

    public static void chuli(){
        List<String> paths=getpath("/home/cxyu/tmp/bdbk-ci");
        for(String path:paths){
        String content="";
        List<String> s=read(path);
        for(int i=0;i<s.size();i++)
            content+=qukong(s.get(i))+"\n";
        appendMethodB(path,content);}
    }


    public static void main(String args[]){
        chuli();
    }
}
