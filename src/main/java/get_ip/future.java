package main.java.get_ip;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by cxyu on 17-6-4.
 */
public class future {


    public static void sd(){
        System.out.println("asdf");
        sd();
    }



    public static void main(String args[]){
        sd();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<String> future =
                new FutureTask<String>(new Callable<String>() {//使用Callable接口作为构造参数
                    public String call() {
                        //真正的任务在这里执行，这里的返回值类型为String，可以为任意类型
                        for(int i=0;i<Integer.MAX_VALUE;i++){
                        System.out.println("shide");
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }}
                        return null;
                    }});
        executor.execute(future);
//在这里可以做别的任何事情




        for(int i=0;i<10000;i++){
            System.out.println("kaishi");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }}




    }
}
