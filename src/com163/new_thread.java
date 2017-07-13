package com163;

/**
 * Created by cxyu on 17-5-13.
 */

 class new_thread extends Thread {
    private String name;

    public new_thread(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("Running " );
        try {
            for(int i = 20; i > 0; i--) {
                System.out.println("Thread: " + name+", " + i);
                // 让线程睡眠一会
                Thread.sleep(50);
            }
        }catch (InterruptedException e) {
            System.out.println("Thread " + " interrupted.");
        }
        System.out.println("Thread " +name + " exiting.");
    }




    public static void main(String args[]) {

        new_thread R2 = new new_thread("one");
        R2.start();
        new_thread R3 = new new_thread("two");
        R3.start();
    }
}
